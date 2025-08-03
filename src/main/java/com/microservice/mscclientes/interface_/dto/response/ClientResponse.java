package com.microservice.mscclientes.interface_.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for client response data.
 * Contains all client information including calculated fields.
 */
@Schema(description = "Response DTO containing client information")
public class ClientResponse {
    
    @Schema(description = "Client's unique identifier", example = "1")
    private Long id;
    
    @Schema(description = "Client's first name", example = "Juan")
    private String firstName;
    
    @Schema(description = "Client's last name", example = "Pérez")
    private String lastName;
    
    @Schema(description = "Client's age", example = "30")
    private Integer age;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Client's birth date", example = "1993-05-15")
    private LocalDate birthDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Estimated death date based on life expectancy", example = "2071-05-15")
    private LocalDate estimatedDeathDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Record creation timestamp", example = "2023-12-01 10:30:00")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Record last update timestamp", example = "2023-12-01 10:30:00")
    private LocalDateTime updatedAt;
    
    // Default constructor
    public ClientResponse() {}
    
    // Constructor with all fields
    public ClientResponse(Long id, String firstName, String lastName, Integer age, 
                         LocalDate birthDate, LocalDate estimatedDeathDate,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.birthDate = birthDate;
        this.estimatedDeathDate = estimatedDeathDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public LocalDate getEstimatedDeathDate() {
        return estimatedDeathDate;
    }
    
    public void setEstimatedDeathDate(LocalDate estimatedDeathDate) {
        this.estimatedDeathDate = estimatedDeathDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "ClientResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", birthDate=" + birthDate +
                ", estimatedDeathDate=" + estimatedDeathDate +
                '}';
    }
}
