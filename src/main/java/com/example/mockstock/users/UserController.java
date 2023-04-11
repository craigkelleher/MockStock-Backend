package com.example.mockstock.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/user")
    public ResponseEntity getUserByID(HttpServletRequest request) {
        Long id = (Long) request.getAttribute("userId");
        User user = userService.getUserByID(id);
        return user == null ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(user);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createNewUser(@RequestBody User user) { return userService.createNewUser(user); }

    @PatchMapping("/user")
    public User updateUser(@RequestBody UserUpdate update, HttpServletRequest request) {
        Long id = (Long) request.getAttribute("userId");
        return userService.updateUser(id, update.getBalance());
    }

    @DeleteMapping("/user")
    public ResponseEntity deleteUser(HttpServletRequest request) {
        Long id = (Long) request.getAttribute("userId");
        try{
            userService.deleteUser(id);
        }catch (UserNotFound e){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.accepted().build();
    }
}
