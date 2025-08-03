package com.microservice.mscclientes.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Client domain entity.
 * Tests business logic and validation rules.
 */
class ClientTest {
    
    @Test
    @DisplayName("Should create client with valid data using builder")
    void shouldCreateClientWithValidData() {
        // Given
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        LocalDateTime now = LocalDateTime.now();
        
        // When
        Client client = Client.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Pérez")
                .age(33)
                .birthDate(birthDate)
                .createdAt(now)
                .updatedAt(now)
                .build();
        
        // Then
        assertNotNull(client);
        assertEquals(1L, client.getId());
        assertEquals("Juan", client.getFirstName());
        assertEquals("Pérez", client.getLastName());
        assertEquals(33, client.getAge());
        assertEquals(birthDate, client.getBirthDate());
        assertEquals(now, client.getCreatedAt());
        assertEquals(now, client.getUpdatedAt());
    }
    
    @Test
    @DisplayName("Should throw exception when first name is null")
    void shouldThrowExceptionWhenFirstNameIsNull() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            Client.builder()
                    .firstName(null)
                    .lastName("Pérez")
                    .age(33)
                    .birthDate(LocalDate.of(1990, 5, 15))
                    .build();
        });
    }
    
    @Test
    @DisplayName("Should throw exception when first name is empty")
    void shouldThrowExceptionWhenFirstNameIsEmpty() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            Client.builder()
                    .firstName("")
                    .lastName("Pérez")
                    .age(33)
                    .birthDate(LocalDate.of(1990, 5, 15))
                    .build();
        });
    }
    
    @Test
    @DisplayName("Should throw exception when age is negative")
    void shouldThrowExceptionWhenAgeIsNegative() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            Client.builder()
                    .firstName("Juan")
                    .lastName("Pérez")
                    .age(-1)
                    .birthDate(LocalDate.of(1990, 5, 15))
                    .build();
        });
    }
    
    @Test
    @DisplayName("Should throw exception when age exceeds 150")
    void shouldThrowExceptionWhenAgeExceeds150() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            Client.builder()
                    .firstName("Juan")
                    .lastName("Pérez")
                    .age(151)
                    .birthDate(LocalDate.of(1990, 5, 15))
                    .build();
        });
    }
    
    @Test
    @DisplayName("Should throw exception when birth date is in the future")
    void shouldThrowExceptionWhenBirthDateIsInFuture() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            Client.builder()
                    .firstName("Juan")
                    .lastName("Pérez")
                    .age(33)
                    .birthDate(LocalDate.now().plusDays(1))
                    .build();
        });
    }
    
    @Test
    @DisplayName("Should calculate estimated death date correctly")
    void shouldCalculateEstimatedDeathDateCorrectly() {
        // Given
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        Client client = Client.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .age(33)
                .birthDate(birthDate)
                .build();
        
        // When
        LocalDate estimatedDeathDate = client.calculateEstimatedDeathDate();
        
        // Then
        assertEquals(LocalDate.of(2068, 5, 15), estimatedDeathDate);
    }
    
    @Test
    @DisplayName("Should return true for adult when age is 18 or more")
    void shouldReturnTrueForAdultWhenAgeIs18OrMore() {
        // Given
        Client client = Client.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .age(18)
                .birthDate(LocalDate.of(2005, 5, 15))
                .build();
        
        // When & Then
        assertTrue(client.isAdult());
    }
    
    @Test
    @DisplayName("Should return false for adult when age is less than 18")
    void shouldReturnFalseForAdultWhenAgeIsLessThan18() {
        // Given
        Client client = Client.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .age(17)
                .birthDate(LocalDate.of(2006, 5, 15))
                .build();
        
        // When & Then
        assertFalse(client.isAdult());
    }
    
    @Test
    @DisplayName("Should return full name correctly")
    void shouldReturnFullNameCorrectly() {
        // Given
        Client client = Client.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .age(33)
                .birthDate(LocalDate.of(1990, 5, 15))
                .build();
        
        // When
        String fullName = client.getFullName();
        
        // Then
        assertEquals("Juan Pérez", fullName);
    }
    
    @Test
    @DisplayName("Should calculate current age correctly")
    void shouldCalculateCurrentAgeCorrectly() {
        // Given
        LocalDate birthDate = LocalDate.now().minusYears(25);
        Client client = Client.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .age(25)
                .birthDate(birthDate)
                .build();
        
        // When
        int currentAge = client.calculateCurrentAge();
        
        // Then
        assertEquals(25, currentAge);
    }
}
