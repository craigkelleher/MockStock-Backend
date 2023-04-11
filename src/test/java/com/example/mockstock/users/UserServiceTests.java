package com.example.mockstock.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        user1 = new User("John123", "John", "Doe", "johnpassword", 1000.0);
        user2 = new User("Jane123", "Jane", "Doe", "janepassword", 2000.0);
    }

    @Test
    void getUserByID() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        User retrievedUser = userService.getUserByID(1L);
        assertNotNull(retrievedUser);
        assertEquals(user1.getName(), retrievedUser.getName());
    }

    @Test
    void createNewUser() {
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User createdUser = userService.createNewUser(user1);
        assertNotNull(createdUser);
        assertEquals(user1.getName(), createdUser.getName());
    }

    @Test
    void deleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        userService.deleteUser(1L);
        verify(userRepository, times(1)).delete(user1);
    }

    @Test
    void deleteUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> userService.deleteUser(1L));
    }

    @Test
    void updateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(user1);

        Double newBalance = 1500.0;
        User updatedUser = userService.updateUser(1L, newBalance);

        assertNotNull(updatedUser);
        assertEquals(newBalance, updatedUser.getBalance());
    }

    @Test
    void updateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User updatedUser = userService.updateUser(1L, 1500.0);
        assertNull(updatedUser);
    }
}
