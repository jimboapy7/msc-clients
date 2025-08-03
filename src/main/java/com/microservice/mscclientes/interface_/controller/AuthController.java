package com.microservice.mscclientes.interface_.controller;

import com.microservice.mscclientes.infrastructure.security.JwtUtil;
import com.microservice.mscclientes.interface_.dto.request.AuthTokenRequest;
import com.microservice.mscclientes.interface_.dto.response.AuthTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication operations.
 * Handles JWT token generation and validation.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication management endpoints")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    private final JwtUtil jwtUtil;
    
    @Value("${spring.security.jwt.expiration}")
    private Long jwtExpiration;
    
    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/token")
    @Operation(
        summary = "Generate JWT token",
        description = "Generates a JWT token for authentication. For demo purposes, accepts any username/password combination."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Token generated successfully",
            content = @Content(schema = @Schema(implementation = AuthTokenResponse.class))
        ),
        @ApiResponse(
            responseCode = "422",
            description = "Invalid request data",
            content = @Content(schema = @Schema(implementation = String.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = String.class))
        )
    })
    public ResponseEntity<AuthTokenResponse> generateToken(@Valid @RequestBody AuthTokenRequest request) {
        logger.info("Token generation request for username: {}", request.getUsername());
        
        try {
            // For demo purposes, we accept any username/password combination
            // In a real application, you would validate credentials against a user store
            if (isValidCredentials(request.getUsername(), request.getPassword())) {
                String token = jwtUtil.generateToken(request.getUsername());
                
                AuthTokenResponse response = new AuthTokenResponse(token, jwtExpiration);
                
                logger.info("Token generated successfully for user: {}", request.getUsername());
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Invalid credentials for user: {}", request.getUsername());
                return ResponseEntity.status(401).build();
            }
        } catch (Exception e) {
            logger.error("Error generating token for user: {}", request.getUsername(), e);
            return ResponseEntity.status(500).build();
        }
    }
    
    /**
     * Simple credential validation for demo purposes.
     * In a real application, this would check against a user database.
     */
    private boolean isValidCredentials(String username, String password) {
        // For demo purposes, accept any non-empty username and password
        // In production, implement proper authentication logic
        return username != null && !username.trim().isEmpty() && 
               password != null && !password.trim().isEmpty();
    }
}
