package com.microservice.mscclientes.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * Client domain entity representing a client in the system.
 * This is a pure domain object with business logic and no framework dependencies.
 */
public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Private constructor for Builder pattern
    private Client() {}

    // Builder pattern implementation
    public static class Builder {
        private Client client = new Client();

        public Builder id(Long id) {
            client.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            client.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            client.lastName = lastName;
            return this;
        }

        public Builder age(Integer age) {
            client.age = age;
            return this;
        }

        public Builder birthDate(LocalDate birthDate) {
            client.birthDate = birthDate;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            client.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            client.updatedAt = updatedAt;
            return this;
        }

        public Client build() {
            validateClient(client);
            return client;
        }

        private void validateClient(Client client) {
            if (client.firstName == null || client.firstName.trim().isEmpty()) {
                throw new IllegalArgumentException("First name cannot be null or empty");
            }
            if (client.lastName == null || client.lastName.trim().isEmpty()) {
                throw new IllegalArgumentException("Last name cannot be null or empty");
            }
            if (client.age == null || client.age < 0 || client.age > 150) {
                throw new IllegalArgumentException("Age must be between 0 and 150");
            }
            if (client.birthDate == null) {
                throw new IllegalArgumentException("Birth date cannot be null");
            }
            if (client.birthDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Birth date cannot be in the future");
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    // Business logic methods
    public LocalDate calculateEstimatedDeathDate() {
        // Using average life expectancy of 78 years
        return this.birthDate.plusYears(78);
    }

    public boolean isAdult() {
        return this.age >= 18;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public int calculateCurrentAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id != null && id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", birthDate=" + birthDate +
                '}';
    }
}
