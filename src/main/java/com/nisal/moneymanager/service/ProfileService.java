package com.nisal.moneymanager.service;

import com.nisal.moneymanager.dto.AuthDTO;
import com.nisal.moneymanager.dto.ProfileDTO;
import com.nisal.moneymanager.entity.ProfileEntity;
import com.nisal.moneymanager.mapper.ProfileMapper;
import com.nisal.moneymanager.repository.ProfileRepository;
import com.nisal.moneymanager.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${app.activation.url}")
    private String activationURL;

    public ProfileDTO registerProfile(ProfileDTO profileDTO) {
        ProfileEntity newProfile = ProfileMapper.toEntity(profileDTO);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile.setPassword(passwordEncoder.encode(newProfile.getPassword()));
        newProfile = profileRepository.save(newProfile);

        //send activation email
        String activationLink = activationURL+"/api/v1.0/activate?token=" + newProfile.getActivationToken();
        String subject = "Activate your Money Manager Account";
        String body = "Click on the following link to activate your Account: " + activationLink;
        emailService.sendEmail(newProfile.getEmail(), subject, body);

        return ProfileMapper.toDTO(newProfile);
    }

    public boolean activateProfile(String activationToken) {
        return profileRepository.findByActivationToken(activationToken)
                .map(profile -> {
                    profile.setIsActive(true);
                    profileRepository.save(profile);
                    return true;
                })
                .orElse(false);
    }

    public boolean isAccountActive(String email) {
        return profileRepository.findByEmail(email)
                .map(ProfileEntity::getIsActive)
                .orElse(false);
    }

    public ProfileEntity getCurrentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return profileRepository.findByEmail(authentication.getName())
                .orElseThrow(
                        () -> new UsernameNotFoundException("Profile not found with email" + authentication.getName())
                );
    }

    public ProfileDTO getPublicProfile(String email) {
        ProfileEntity user;

        if (email == null) {
            user = getCurrentProfile();
        } else {
            user = profileRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: " + email));
        }

        return ProfileMapper.toDTO(user);
    }

    public Map<String, Object> authenticateAndGenerateToken(AuthDTO authDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword())
            );

            String token = jwtUtil.generateToken(authDTO.getEmail());

            // ❌ This line is wrong during login:
            // ProfileDTO user = getPublicProfile(authDTO.getEmail());

            // ✅ Instead: fetch user by email directly
            ProfileEntity user = profileRepository.findByEmail(authDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: " + authDTO.getEmail()));

            return Map.of(
                    "token", token,
                    "user", getPublicProfile(authDTO.getEmail())
            );
        } catch (Exception e) {
            e.printStackTrace(); // log full error
            throw new RuntimeException("Invalid username or password", e);
        }
    }
}
