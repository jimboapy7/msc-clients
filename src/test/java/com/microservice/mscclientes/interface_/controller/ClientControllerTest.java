package com.microservice.mscclientes.interface_.controller;

import com.microservice.mscclientes.application.usecase.CreateClientUseCase;
import com.microservice.mscclientes.application.usecase.GetAllClientsUseCase;
import com.microservice.mscclientes.application.usecase.GetClientMetricsUseCase;
import com.microservice.mscclientes.domain.entity.Client;
import com.microservice.mscclientes.domain.valueobject.ClientMetrics;
import com.microservice.mscclientes.interface_.mapper.ClientDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ClientController.
 * Tests REST endpoint behavior and error handling.
 */
@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
    
    @Mock
    private CreateClientUseCase createClientUseCase;
    
    @Mock
    private GetAllClientsUseCase getAllClientsUseCase;
    
    @Mock
    private GetClientMetricsUseCase getClientMetricsUseCase;
    
    @Mock
    private ClientDtoMapper dtoMapper;
    
    private ClientController clientController;
    
    @BeforeEach
    void setUp() {
        clientController = new ClientController(
                createClientUseCase,
                getAllClientsUseCase,
                getClientMetricsUseCase,
                dtoMapper
        );
    }
    
    @Test
    @DisplayName("Should return all clients successfully")
    void shouldReturnAllClientsSuccessfully() {
        // Given
        List<Client> clients = Arrays.asList(
                Client.builder()
                        .id(1L)
                        .firstName("Juan")
                        .lastName("Pérez")
                        .age(30)
                        .birthDate(LocalDate.of(1993, 5, 15))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
        
        when(getAllClientsUseCase.execute()).thenReturn(clients);
        when(dtoMapper.toResponseDtoList(clients)).thenReturn(Arrays.asList());
        
        // When
        ResponseEntity<?> response = clientController.getAllClients();
        
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(getAllClientsUseCase, times(1)).execute();
        verify(dtoMapper, times(1)).toResponseDtoList(clients);
    }
    
    @Test
    @DisplayName("Should return client metrics successfully")
    void shouldReturnClientMetricsSuccessfully() {
        // Given
        ClientMetrics metrics = new ClientMetrics(35.5, 12.3, 150);
        
        when(getClientMetricsUseCase.execute()).thenReturn(metrics);
        when(dtoMapper.toMetricsResponseDto(metrics)).thenReturn(null);
        
        // When
        ResponseEntity<?> response = clientController.getClientMetrics();
        
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(getClientMetricsUseCase, times(1)).execute();
        verify(dtoMapper, times(1)).toMetricsResponseDto(metrics);
    }
    
    @Test
    @DisplayName("Should handle exception when getting all clients")
    void shouldHandleExceptionWhenGettingAllClients() {
        // Given
        when(getAllClientsUseCase.execute()).thenThrow(new RuntimeException("Database error"));
        
        // When
        ResponseEntity<?> response = clientController.getAllClients();
        
        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(getAllClientsUseCase, times(1)).execute();
    }
    
    @Test
    @DisplayName("Should handle exception when getting client metrics")
    void shouldHandleExceptionWhenGettingClientMetrics() {
        // Given
        when(getClientMetricsUseCase.execute()).thenThrow(new RuntimeException("Database error"));
        
        // When
        ResponseEntity<?> response = clientController.getClientMetrics();
        
        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(getClientMetricsUseCase, times(1)).execute();
    }
}
