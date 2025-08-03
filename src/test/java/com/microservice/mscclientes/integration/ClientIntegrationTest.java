package com.microservice.mscclientes.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.mscclientes.infrastructure.security.JwtUtil;
import com.microservice.mscclientes.interface_.dto.request.CreateClientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the Client API endpoints.
 * Tests the complete flow from HTTP request to database.
 */
@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class ClientIntegrationTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private MockMvc mockMvc;
    private String jwtToken;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        
        // Generate JWT token for authenticated requests
        jwtToken = jwtUtil.generateToken("testuser");
    }
    
    @Test
    @DisplayName("Should generate JWT token successfully")
    void shouldGenerateJwtTokenSuccessfully() throws Exception {
        // Given
        String requestBody = """
                {
                    "username": "admin",
                    "password": "password123"
                }
                """;
        
        // When & Then
        mockMvc.perform(post("/api/v1/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").exists());
    }
    
    @Test
    @DisplayName("Should create client successfully with valid data")
    void shouldCreateClientSuccessfullyWithValidData() throws Exception {
        // Given
        CreateClientRequest request = new CreateClientRequest(
                "Juan",
                "Pérez",
                30,
                LocalDate.of(1993, 5, 15)
        );
        
        String requestBody = objectMapper.writeValueAsString(request);
        
        // When & Then
        mockMvc.perform(post("/api/v1/clients")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Pérez"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.birthDate").value("1993-05-15"))
                .andExpect(jsonPath("$.estimatedDeathDate").value("2071-05-15"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }
    
    @Test
    @DisplayName("Should return validation error for invalid client data")
    void shouldReturnValidationErrorForInvalidClientData() throws Exception {
        // Given
        String requestBody = """
                {
                    "firstName": "",
                    "lastName": "Pérez",
                    "age": -1,
                    "birthDate": "2025-01-01"
                }
                """;
        
        // When & Then
        mockMvc.perform(post("/api/v1/clients")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.fieldErrors").exists());
    }
    
    @Test
    @DisplayName("Should return unauthorized when no JWT token provided")
    void shouldReturnUnauthorizedWhenNoJwtTokenProvided() throws Exception {
        // Given
        String requestBody = """
                {
                    "firstName": "Juan",
                    "lastName": "Pérez",
                    "age": 30,
                    "birthDate": "1993-05-15"
                }
                """;
        
        // When & Then
        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("Unauthorized"));
    }
    
    @Test
    @DisplayName("Should get all clients successfully")
    void shouldGetAllClientsSuccessfully() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/clients")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    @DisplayName("Should get client metrics successfully")
    void shouldGetClientMetricsSuccessfully() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/clients/metrics")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageAge").exists())
                .andExpect(jsonPath("$.standardDeviation").exists())
                .andExpect(jsonPath("$.totalClients").exists());
    }
}
