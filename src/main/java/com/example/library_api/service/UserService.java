package com.example.library_api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.library_api.model.User;
import com.example.library_api.model.UserVerification;
import com.example.library_api.repository.UserRepository;
import com.example.library_api.repository.UserVerificationRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final UserVerificationRepository userVerificationRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmailService emailService;
    private final Map<String, String> verificationCodes = new HashMap<>(); // Email - Code mapping

    public UserService(EmailService emailService, UserVerificationRepository userVerificationRepository) {
        this.emailService = emailService;
        this.userVerificationRepository = userVerificationRepository;
    }    

    // Method to register a new user
    public String registerUser(String email, String password, String name, int birthYear) {
        // Check if email is already waiting for verification
        if (verificationCodes.containsKey(email)) {
            throw new IllegalArgumentException("Email is already waiting for verification.");
        }
    
        // Generate verification code
        String verificationCode = generateVerificationCode();
    
        // Create user verification object
        UserVerification userVerification = new UserVerification();
        userVerification.setEmail(email);
        userVerification.setCode(verificationCode);
        userVerification.setPassword(password);  // Save password temporarily for verification
        userVerification.setName(name);
        userVerification.setBirthYear(birthYear);
    
        // Save verification data in database
        userVerificationRepository.save(userVerification);
    
        // Send verification email
        emailService.sendEmail(email, "Verification Code", "Your verification code: " + verificationCode);
    
        return "A verification code has been sent to your email. You can complete your registration by entering the code.";
    }
    
    // Method to verify the user and complete registration
    public String verifyUser(String email, String code) {
        // Check if a verification record exists for the provided email
        Optional<UserVerification> verificationOptional = userVerificationRepository.findByEmail(email);
    
        if (verificationOptional.isEmpty()) {
            throw new IllegalArgumentException("Verification code not found for this email address.");
        }
    
        UserVerification verification = verificationOptional.get();
    
        // Check if the provided code matches the one in the verification record
        if (!verification.getCode().equals(code)) {
            throw new IllegalArgumentException("Incorrect verification code.");
        }
      
        // Extract user details from verification record
        String password = verification.getPassword();
        String name = verification.getName();
        int birthYear = verification.getBirthYear();

        // Create user object and save to database
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Hash the password
        user.setName(name);  
        user.setBirthyear(birthYear);  

        try {
            userRepository.save(user); // Save user to the repository
            System.out.println("User successfully registered.");
        } catch (Exception e) {
            System.out.println("Error occurred while saving user: " + e.getMessage());
            throw new IllegalStateException("User could not be saved.");
        }

        // Delete the verification record after successful registration
        try {
            userVerificationRepository.delete(verification);
            System.out.println("Verification record deleted.");
        } catch (Exception e) {
            System.out.println("Error occurred while deleting verification record: " + e.getMessage());
            throw new IllegalStateException("Verification record could not be deleted.");
        }

        return "User successfully registered.";
    }
    
    // Helper method to generate a random 6-digit verification code
    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999)); // Generate 6-digit code
    }

    // Method to log in a user
    public String loginUser(String email, String password) {
        // Search for user by email
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get(); // Get the user

            // Verify the hashed password
            if (passwordEncoder.matches(password, user.getPassword())) {
                return "Login successful!";
            } else {
                throw new IllegalArgumentException("Invalid email or password.");
            }
        } else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }

    // Method to change the verification code for a user
    public String changeUserCode(String email) {
        // Check if the email is valid
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be empty.");
        }
    
        // Check if email is already waiting for verification
        if (verificationCodes.containsKey(email)) {
            throw new IllegalArgumentException("Email is already waiting for verification.");
        }
    
        // Generate a new verification code
        String verificationCode = generateVerificationCode();
    
        // Create new verification record
        UserVerification userVerification = new UserVerification();
        userVerification.setEmail(email);
        userVerification.setCode(verificationCode);
    
        // Save the verification data
        userVerificationRepository.save(userVerification);
    
        // Send the verification code to the user's email
        emailService.sendEmail(email, "Verification Code", "Your new verification code: " + verificationCode);
    
        return "A new verification code has been sent to your email. You can complete your registration by entering the code.";
    }
    
    public String verifyUserForChangeCode(String email, String code) {
        // Check if email is null or empty
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be empty.");
        }
        // Check if code is null or empty
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Verification code cannot be empty.");
        }
    
        // Search for the verification code in the database using the provided email
        Optional<UserVerification> verificationOptional = userVerificationRepository.findByEmail(email);
    
        if (verificationOptional.isEmpty()) {
            throw new IllegalArgumentException("No verification code found for the provided email address.");
        }
    
        UserVerification verification = verificationOptional.get();
    
        // Check if the provided code matches the saved code
        if (!verification.getCode().equals(code)) {
            throw new IllegalArgumentException("Invalid verification code.");
        }
    
        // If verification is successful, delete the verification record
        userVerificationRepository.delete(verification);
    
        // Complete any other necessary actions (e.g., updating user information)
        return "User verification process completed successfully.";
    }
    
    public String changeUserCodeEnd(String email, String newPassword) {
        // Check if email is null or empty
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be empty.");
        }
        // Check if new password is null or empty
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty.");
        }
    
        // If email is still waiting for verification, return an error
        if (verificationCodes.containsKey(email)) {
            throw new IllegalArgumentException("Email is awaiting verification. Cannot perform operation.");
        }
    
        // Check if a user with the given email exists
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("No user found with this email address.");
        }
    
        // Perform password reset operation
        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword)); 
        userRepository.save(user); // Update the user with the new password
    
        return "Password successfully updated. You can now use the new password.";
    }
    
    public String giveUserTierAndRetract(String email, String tier) {
        // Check if email is null or empty
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be empty.");
        }
        // Check if tier is null or empty
        if (tier == null || tier.isEmpty()) {
            throw new IllegalArgumentException("Tier information cannot be empty.");
        }
    
        // If email is awaiting verification, return an error
        if (verificationCodes.containsKey(email)) {
            throw new IllegalArgumentException("Email is awaiting verification. Cannot perform operation.");
        }
    
        // Check if a user with the given email exists
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("No user found with this email address.");
        }
    
        // Perform tier assignment operation
        User user = userOptional.get();
        user.setTier(tier); // Assign the tier to the user
        userRepository.save(user); // Update the user with the new tier
    
        return "User tier information successfully updated.";
    }
    
    public String updateUser(String email, String password, String name, int birthYear) {
        // Check if email is awaiting verification
        if (verificationCodes.containsKey(email)) {
            throw new IllegalArgumentException("Email is awaiting verification.");
        }
    
        // Search for the user by email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("No user found with this email address.");
        }
    
        // Get the user record
        User user = userOptional.get();
        
        // Update user information if provided
        if (name != null) user.setName(name);
        if (birthYear > 0) user.setBirthyear(birthYear);
        if (password != null && !password.isEmpty()) user.setPassword(passwordEncoder.encode(password));
    
        // Save the updated user
        userRepository.save(user);
    
        return "User successfully updated.";
    }
    
    public List<User> searchUser(String email, String name, Integer birthyear, String tier) {
        try {
            // Search for the user based on given criteria
            Optional<User> user;

            if (email != null) {
                user = userRepository.findByEmail(email);
            } else if (name != null) {
                user = userRepository.findByName(name);
            } else if (birthyear != null) {
                user = userRepository.findByBirthyear(birthyear);
            } else if (tier != null) {
                user = userRepository.findByTier(tier);
            } else {
                return userRepository.findAll(); 
            }

            if (user.isPresent()) {
                return List.of(user.get());
            } else {
                throw new IllegalArgumentException("No user found matching the criteria.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("An error occurred while searching for users.", e);
        }
    }

    public void deleteUser(String email) {
        // Search for the user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found."));

        // Delete the user from the database
        userRepository.delete(user);
    }

    // Returns a map of email addresses with their verification codes
    public Map<String, String> getVerificationCodes() {
        return verificationCodes;
    }
}

