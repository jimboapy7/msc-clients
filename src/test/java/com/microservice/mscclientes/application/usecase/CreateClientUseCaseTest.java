package com.microservice.mscclientes.application.usecase;

import com.microservice.mscclientes.domain.entity.Client;
import com.microservice.mscclientes.domain.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CreateClientUseCase.
 * Tests the business logic for client creation.
 */
@ExtendWith(MockitoExtension.class)
class CreateClientUseCaseTest {
    
    @Mock
    private ClientRepository clientRepository;
    
    private CreateClientUseCase createClientUseCase;
    
    @BeforeEach
    void setUp() {
        createClientUseCase = new CreateClientUseCase(clientRepository);
    }
    
    @Test
    @DisplayName("Should create client successfully")
    void shouldCreateClientSuccessfully() {
        // Given
        Client inputClient = Client.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .age(30)
                .birthDate(LocalDate.of(1993, 5, 15))
                .build();
        
        Client savedClient = Client.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Pérez")
                .age(30)
                .birthDate(LocalDate.of(1993, 5, 15))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);
        
        // When
        Client result = createClientUseCase.execute(inputClient);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getFirstName());
        assertEquals("Pérez", result.getLastName());
        assertEquals(30, result.getAge());
        assertEquals(LocalDate.of(1993, 5, 15), result.getBirthDate());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        
        verify(clientRepository, times(1)).save(any(Client.class));
    }
    
    @Test
    @DisplayName("Should handle repository exception")
    void shouldHandleRepositoryException() {
        // Given
        Client inputClient = Client.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .age(30)
                .birthDate(LocalDate.of(1993, 5, 15))
                .build();
        
        when(clientRepository.save(any(Client.class))).thenThrow(new RuntimeException("Database error"));
        
        // When & Then
        assertThrows(RuntimeException.class, () -> {
            createClientUseCase.execute(inputClient);
        });
        
        verify(clientRepository, times(1)).save(any(Client.class));
    }
}
