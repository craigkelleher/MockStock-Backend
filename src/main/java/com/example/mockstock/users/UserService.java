package com.example.mockstock.users;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }
    public User getUserByID(String id) { return userRepository.findById(id).orElse(null); }
    public User createNewUser(User user) { return userRepository.createUser(user); }

    public void deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.delete(user.get());
        } else {
            throw new UserNotFound();
        }
    }
    public User updateUser(String name, String email, String password, BigDecimal balance, String id) {
        Optional<User> update = userRepository.findById(id);
        if (update.isPresent()) {
            update.setEmail(email);
            update.setName(name);
            update.setPassword(password);
            update.setBalance(balance);
            return userRepository.save(update.get());
        }
        return null;
    }
}
