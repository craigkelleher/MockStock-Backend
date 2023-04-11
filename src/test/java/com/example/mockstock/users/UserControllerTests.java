//package com.example.mockstock.users;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(UserController.class)
//@ExtendWith(MockitoExtension.class)
//public class UserControllerTests {
//    @MockBean
//    UserService userService;
//
//    @InjectMocks
//    UserController userController;
//
//    MockMvc mockMvc;
//
//    User user1;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//        user1 = new User("John", "john@example.com", "johnpassword", 1000.0);
//    }
//
//    @Test
//    void getUserByID() throws Exception {
//        when(userService.getUserByID(1L)).thenReturn(user1);
//
//        mockMvc.perform(get("/api/user/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("John"));
//    }
//
//    @Test
//    void createNewUser() throws Exception {
//        when(userService.createNewUser(any(User.class))).thenReturn(user1);
//
//        mockMvc.perform(post("/api/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"John\",\"email\":\"john@example.com\",\"password\":\"johnpassword\",\"balance\":1000.0}"))
//                .andExpect(status().isCreated()) // Change the expected status code to 201 CREATED
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("John"));
//    }
//
//    @Test
//    void updateUser() throws Exception {
//        when(userService.updateUser(1L, 1500.00)).thenReturn(new User("John", "john@example.com", "johnpassword", 1500.0));
//
//        mockMvc.perform(patch("/api/user/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("1500.0"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.balance").value(1500.0));
//    }
//
//    @Test
//    void deleteUser() throws Exception {
//        mockMvc.perform(delete("/api/user/1"))
//                .andExpect(status().isAccepted());
//    }
//
//    @Test
//    void deleteUserNotFound() throws Exception {
//        doThrow(new UserNotFound()).when(userService).deleteUser(1L);
//
//        mockMvc.perform(delete("/api/user/1"))
//                .andExpect(status().isNoContent());
//    }
//}
