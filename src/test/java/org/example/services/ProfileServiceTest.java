package org.example.services;

import org.example.models.Profile;
import org.example.models.User;
import org.example.repositories.ProfileRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProfileService profileService;

    public ProfileServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserProfile_InvalidId() {
        System.out.println("Running testGetUserProfile_InvalidId");
        assertThrows(IllegalArgumentException.class, () -> {
            profileService.getUserProfile(null, 123L);
        },"Failed test");
    }

    @Test
    public void testGetUserProfile_InvalidPassword() {
        System.out.println("Running testGetUserProfile_InvalidPassword");
        assertThrows(IllegalArgumentException.class, () -> {
            profileService.getUserProfile(1L, 20L);
        },"Failed test");
    }

    @Test
    public void testUpdateProfile_NotFound() {
        System.out.println("Running testUpdateProfile_NotFound");
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        String result = profileService.updateProfile(1L, "test@test.com", "bio");
        assertEquals("Profile not found for user with ID 1", result,"Failed test");
    }

 //create sucssess message for profile update
    @Test
    public void testUpdateProfile_Success() {
        System.out.println("Running testUpdateProfile_Success");
        Profile profile = new Profile();
        profile.setUser_id(1L);
        profile.setEmail("oldMail@gmail.com");
        profile.setBio("New bio");
        User user = new User();
        user.setId(1L);
        user.setEmail("oldMail@gmail.com");
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        String result = profileService.updateProfile(1L, "NewMail@gmail.com", "New bio");
        assertEquals("Profile updated successfully for user with ID 1", result, "Failed test");
    }


    @Test
    public void testGetUserProfile_Success() {
        System.out.println("Running testGetUserProfile_Success");
        User user = new User();
        user.setId(1L);
        user.setPassword(1234L);
        Profile profile = new Profile();
        profile.setUser_id(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));

        Profile result = profileService.getUserProfile(1L, 1234L);
        assertNotNull(result, "Failed test");
        assertEquals(1L, result.getUser_id(), "Failed test");
    }
}
