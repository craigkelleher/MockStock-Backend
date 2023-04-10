package com.example.mockstock.config;

import com.example.mockstock.users.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@RestController
public class LoginController {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expirationTime}")
    private int EXPIRATION_TIME;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentials userCredentials) {

        String sql = "SELECT * FROM users WHERE user_name = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{userCredentials.getUsername()}, (resultSet, i) -> {
                User u = new User();
                u.setName(resultSet.getString("user_name"));
                u.setPassword(resultSet.getString("user_password"));
                u.setId(resultSet.getLong("id"));
                return u;
            });
            if (bCryptPasswordEncoder.matches(userCredentials.getPassword(), user.getPassword())) {
                // Authentication successful
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userCredentials.getUsername());
                String token = jwtTokenUtil.generateToken(userDetails);
                return ResponseEntity.ok(new JwtResponse(token));
            }
        } catch (EmptyResultDataAccessException ex) {
            // User not found
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Authentication failed
    }
}

