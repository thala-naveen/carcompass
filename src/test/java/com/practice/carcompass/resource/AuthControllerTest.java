package com.practice.carcompass.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.carcompass.domain.User;
import com.practice.carcompass.dto.AuthRequest;
import com.practice.carcompass.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfi
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Clear the user we're going to create in signup test to avoid DuplicateKeyException
        userRepository.findByEmail("testsignup@example.com")
                .ifPresent(user -> userRepository.delete(user));
    }

    @Test
    void testSignupSuccess() throws Exception {
        AuthRequest signupRequest = new AuthRequest();
        signupRequest.setEmail("testsignup@example.com");
        signupRequest.setPassword("password123");

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isCreated());

        Optional<User> user = userRepository.findByEmail("testsignup@example.com");
        assertTrue(user.isPresent());
    }

    @Test
    void testLoginSuccess() throws Exception {
        // Note: admin@carcompass.com is seeded by Mongock
        AuthRequest loginRequest = new AuthRequest();
        loginRequest.setEmail("admin@carcompass.com");
        loginRequest.setPassword("admin123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}
