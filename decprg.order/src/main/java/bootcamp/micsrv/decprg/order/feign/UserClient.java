package bootcamp.micsrv.decprg.order.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import bootcamp.micsrv.decprg.order.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@FeignClient(name = "user-service", url ="${userBaseUrl}")
public interface UserClient {

    @GetMapping
    List<User> getAllUsers();

    @GetMapping("/{userId}")
    User getUserById(@PathVariable @NotNull Long userId);

    @PostMapping
    ResponseEntity<?> createUser(@Valid @RequestBody User user);

    @PutMapping("/{userId}")
    ResponseEntity<?> updateUser(@PathVariable @NotNull Long userId, @Valid @RequestBody User updatedUser);

    @DeleteMapping("/{userId}")
    ResponseEntity<?> deleteUser(@PathVariable @NotNull Long userId);
}