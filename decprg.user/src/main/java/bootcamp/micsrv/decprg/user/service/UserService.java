package bootcamp.micsrv.decprg.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import bootcamp.micsrv.decprg.user.exception.UserNotFoundException;
import bootcamp.micsrv.decprg.user.model.User;
import bootcamp.micsrv.decprg.user.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Service
@Validated
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createUser(@Valid User user) {
        logger.info("Creating user: {}", user);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        logger.info("Retrieving all users");
        return userRepository.findAll();
    }

    public User getUserById(@NotNull Long userId) {
        logger.info("Retrieving user by ID: {}", userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Transactional
    public User updateUser(@NotNull Long userId, @Valid User updatedUser) {
        logger.info("Updating user with ID: {}", userId);

        User existingUser = getUserById(userId);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setActive(updatedUser.isActive());

        logger.info("User updated: {}", existingUser);
        return existingUser;
    }

    @Transactional
    public void deleteUser(@NotNull Long userId) {
        logger.info("Deleting user with ID: {}", userId);

        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            logger.info("User deleted with ID: {}", userId);
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }
}
