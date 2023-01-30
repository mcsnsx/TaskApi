package com.example.taskApi.taskApi.Controller;

import com.example.taskApi.taskApi.Model.User;
import com.example.taskApi.taskApi.Repository.UserRepository;
import com.example.taskApi.taskApi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/user")
    public ResponseEntity<User> registerUser(@Validated @RequestBody User user) {
        return userService.registerUser(user)
                .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @Validated @RequestBody User userDetails) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setTask(userDetails.getTask());
        final User update = userRepository.save(user);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/user/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        userRepository.delete(user);
        Map<String, Boolean> resp = new HashMap<>();
        resp.put("deleted", Boolean.TRUE);
        return resp;
    }
}
