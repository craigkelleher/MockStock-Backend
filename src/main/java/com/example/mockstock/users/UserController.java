package com.example.mockstock.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable Long id) {
        User user = userService.getUserByID(id);
        return user == null ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(user);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createNewUser(@RequestBody User user) { return userService.createNewUser(user); }

    @PatchMapping("/user/{id}")
    public User updateUser(@RequestBody Double balance, @PathVariable Long id) {
        return userService.updateUser(id, balance);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try{
            userService.deleteUser(id);
        }catch (UserNotFound e){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.accepted().build();
    }
}
