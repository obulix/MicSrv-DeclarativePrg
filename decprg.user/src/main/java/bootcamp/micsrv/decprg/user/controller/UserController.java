package bootcamp.micsrv.decprg.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import bootcamp.micsrv.decprg.user.exception.UserNotFoundException;
import bootcamp.micsrv.decprg.user.model.User;
import bootcamp.micsrv.decprg.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        logger.info("Received request to create user: {}", user);

        if (result.hasErrors()) {
            // Validation errors occurred
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            User createdUser = userService.createUser(user);
            logger.info("User created with ID: {}", createdUser.getId());
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Received request to retrieve all users");

        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable @NotNull Long userId) {
        logger.info("Received request to retrieve user by ID: {}", userId);

        try {
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.error("User not found with ID: {}", userId);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Update user by ID
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable @NotNull Long userId, @Valid @RequestBody User updatedUser,
                                       BindingResult result) {
        logger.info("Received request to update user with ID: {}", userId);

        if (result.hasErrors()) {
            // Validation errors occurred
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            User user = userService.updateUser(userId, updatedUser);
            logger.info("User updated with ID: {}", userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.error("User not found with ID: {}", userId);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Delete user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable @NotNull Long userId) {
        logger.info("Received request to delete user with ID: {}", userId);

        try {
            userService.deleteUser(userId);
            logger.info("User deleted with ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            logger.error("User not found with ID: {}", userId);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
