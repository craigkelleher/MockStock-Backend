package com.example.mockstock.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/user/{id}")
    public User getUserByID(@PathVariable String id) { return userService.getUserByID(id); }
    @PostMapping("/user")
    public User createNewUser(@RequestBody User user) { return userService.createNewUser(user); }
    @PatchMapping("/user/{id}")
    public User updateUser(@RequestBody Double balance, @PathVariable String id) {
        Double thisBalance = balance;
        return userService.updateUser(thisBalance, id);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        try{
            userService.deleteUser(id);
        }catch (UserNotFound e){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.accepted().build();
    }
}
