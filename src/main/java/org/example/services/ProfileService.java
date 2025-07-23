package org.example.services;

import org.example.cache.CacheAlgo;
import org.example.cache.LRUCacheAlgo;
import org.example.models.Profile;
import org.example.models.User;
import org.example.repositories.ProfileRepository;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserRepository userRepository;

    // Cache for profiles by userId
    private final CacheAlgo<Long, Profile> profileCache = new LRUCacheAlgo<>(100);

    // -- user login: get profile by user ID and password
    public Profile getUserProfile(Long id, Long password) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Error: user ID is invalid");
        }
        if (password == null || password < 100) {
            throw new IllegalArgumentException("Error: Password must be longer than 3 digits");
        }

        User u = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: one or more of the parameters are incorrect"));

        if (!u.getPassword().equals(password)) {
            throw new RuntimeException("Error: one or more of the parameters are incorrect");
        }

        // Check profile in cache first
        if (profileCache.containsKey(id)) {
            System.out.println("Profile found in cache for user ID: " + id);
            return profileCache.get(id);
        }
        System.out.println("Profile not found in cache, fetching from database for user ID: " + id);

        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Profile not found for user with ID " + id));

        profileCache.put(id, profile);
        System.out.println("Profile added to cache for user ID: " + id);
        return profile;
    }

    // -- Update user profile with email and bio
    public String updateProfile(Long userId, String email, String bio) {
        Profile profile = profileRepository.findById(userId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (profile == null || user == null) {
            return "Profile not found for user with ID " + userId;
        }

        profile.setEmail(email);
        profile.setBio(bio);
        profileRepository.save(profile);

        user.setEmail(email);
        userRepository.save(user);

        // Update profile cache:
        profileCache.put(userId, profile);
        System.out.println("Profile updated in cache for user ID: " + userId);

        return "Profile updated successfully for user with ID " + userId;
    }
}
