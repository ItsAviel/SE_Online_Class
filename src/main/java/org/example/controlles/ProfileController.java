package org.example.controlles;

import org.example.models.Profile;
import org.example.models.UserProfileRequest;
import org.example.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;


    @PostMapping
    public ResponseEntity<?> getUserProfile(@RequestBody UserProfileRequest request) {
        try {
            Profile profile = profileService.getUserProfile(request.getId(), request.getPassword());
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }







}
