package com.example.mockstock.config;

import com.example.mockstock.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class LoginController {

    @Autowired
    DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentials userCredentials) {

        String sql = "SELECT user_name, user_password FROM users WHERE user_name = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{userCredentials.getUsername()}, (resultSet, i) -> {
                User u = new User();
                u.setName(resultSet.getString("user_name"));
                u.setPassword(resultSet.getString("user_password"));
                return u;
            });
            if (bCryptPasswordEncoder.matches(userCredentials.getPassword(), user.getPassword())) {
                // Authentication successful
                return ResponseEntity.ok().build();
            }
        } catch (EmptyResultDataAccessException ex) {
            // User not found
        }

        // Authentication failed
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

