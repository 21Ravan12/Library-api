package com.example.library_api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library_api.dto.LoginRequest;
import com.example.library_api.dto.UserRequest;
import com.example.library_api.model.User;
import com.example.library_api.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Handles user registration request
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        try {
            // Attempts to register the user by calling the service method
            String message = userService.registerUser(
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getName(),
                userRequest.getBirthyear()
            );
            return ResponseEntity.ok(message); // Returns success message
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns error message in case of validation failure
        }
    }

    // Verifies the user during the registration process
    @PostMapping("/verifyForRegister")
    public ResponseEntity<?> verifyForRegister(@RequestBody Map<String, String> request) {
        try {
            // Retrieves email and verification code from the request
            String email = request.get("email");
            String code = request.get("code");
            String message = userService.verifyUser(email, code); // Verifies user using the service
            
            return ResponseEntity.ok(message); // Returns success message
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns error message if verification fails
        }
    }

    // Handles user login request
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Attempts to log in the user
            String message = userService.loginUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            );
            return ResponseEntity.ok(message); // Returns success message if login is successful
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns error message if login fails
        }
    }

    // Changes the verification code for the user
    @PostMapping("/changeCode")
    public ResponseEntity<?> changeCode(@RequestBody UserRequest userRequest) {
        try {
            // Retrieves the email to change the code
            String email = userRequest.getEmail();
            String message = userService.changeUserCode(email); // Changes the user's code
            return ResponseEntity.ok(message); // Returns success message
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns error message if code change fails
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // Verifies the user's new verification code for changing the code
    @PostMapping("/verifyForChangeCode")
    public ResponseEntity<?> verifyForChangeCode(@RequestBody Map<String, String> request) {
        try {
            // Retrieves email and verification code from the request
            String email = request.get("email");
            String code = request.get("code");
            
            // Null or empty value check
            if (email == null || email.isEmpty() || code == null || code.isEmpty()) {
                return ResponseEntity.badRequest().body("Email and code must not be empty.");
            }

            // Verifies user for changing the code
            String message = userService.verifyUserForChangeCode(email, code);
            return ResponseEntity.ok(message); // Returns success message
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns error message if verification fails
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // Finalizes the change of verification code
    @PostMapping("/changeCodeEnd")
    public ResponseEntity<?> changeCodeEnd(@RequestBody UserRequest userRequest) {
        try {
            // Changes the verification code after validation
            String message = userService.changeUserCodeEnd(
                userRequest.getEmail(),
                userRequest.getPassword()
            );
            return ResponseEntity.ok(message); // Returns success message
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns error message if code change fails
        }
    }

    // Assigns or revokes the user's tier based on the request
    @PostMapping("/giveTierAndRetract")
    public ResponseEntity<?> giveTierAndRetract(@RequestBody UserRequest userRequest) {
        try {
            // Assigns or revokes tier based on the user request
            String message = userService.giveUserTierAndRetract(
                userRequest.getEmail(),
                userRequest.getTier()
            );
            return ResponseEntity.ok(message); // Returns success message
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns error message if tier assignment fails
        }
    }

    // Updates user information based on the request
    @PostMapping("/update")
    public ResponseEntity<?> uptade(@RequestBody UserRequest userRequest) {
        try {
            // Updates the user information (email, password, name, birth year)
            String message = userService.updateUser(
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getName(),
                userRequest.getBirthyear()
            );
            return ResponseEntity.ok(message); // Returns success message
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns error message if update fails
        }
    }

    // Searches for users based on provided criteria
    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestBody UserRequest userRequest) {
        // Retrieves the parameters for the search, assigns null where necessary
        String email = userRequest.getEmail() != null ? userRequest.getEmail() : null;
        String name = userRequest.getName() != null ? userRequest.getName() : null;
        Integer age = userRequest.getBirthyear() > 0 ? userRequest.getBirthyear() : null;
        String role = userRequest.getTier() != null ? userRequest.getTier() : null;

        try {
            // Calls the service to search for users based on the provided criteria
            List<User> userInfo = userService.searchUser(email, name, age, role);
            
            // Returns the found users or a not found message if no users match the criteria
            if (userInfo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found matching the criteria.");
            }
            return ResponseEntity.ok(userInfo); // Returns the list of found users
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred while searching for users.");
        }
    }

    // Deletes the user based on the provided email address
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody UserRequest userRequest) {
        // Retrieves the email address for deleting the user
        String email = userRequest.getEmail();

        // Null or empty value check for the email
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("Invalid input: Email parameter is required.");
        }

        try {
            // Deletes the user using the email address
            userService.deleteUser(email.trim());
            return ResponseEntity.ok("User with email " + email + " has been successfully deleted.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Returns error message if deletion fails
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred.");
        }
    }
}
