package org.example.services;

import org.example.models.Profile;
import org.example.models.User;
import org.example.repositories.ProfileRepository;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserRepository userRepository;

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

        return profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Profile not found for user with ID " + id));
    }


    public String updateProfile(Long userId, String email, String bio) {
        //Update the user's profile with the given email and bio
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
        return "Profile updated successfully for user with ID " + userId;
    }



}
