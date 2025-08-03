package com.microservice.mscclientes.domain.repository;

import com.microservice.mscclientes.domain.entity.Client;
import com.microservice.mscclientes.domain.valueobject.ClientMetrics;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Client entity.
 * Defines the contract for data access operations.
 * This is part of the domain layer and should not have framework dependencies.
 */
public interface ClientRepository {
    
    /**
     * Save a client to the repository
     * @param client the client to save
     * @return the saved client with generated ID
     */
    Client save(Client client);
    
    /**
     * Find a client by ID
     * @param id the client ID
     * @return Optional containing the client if found
     */
    Optional<Client> findById(Long id);
    
    /**
     * Find all clients
     * @return list of all clients
     */
    List<Client> findAll();
    
    /**
     * Delete a client by ID
     * @param id the client ID to delete
     */
    void deleteById(Long id);
    
    /**
     * Check if a client exists by ID
     * @param id the client ID
     * @return true if client exists
     */
    boolean existsById(Long id);
    
    /**
     * Count total number of clients
     * @return total count of clients
     */
    long count();
    
    /**
     * Calculate client age metrics
     * @return ClientMetrics containing average age and standard deviation
     */
    ClientMetrics calculateAgeMetrics();
}
