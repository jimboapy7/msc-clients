package com.microservice.mscclientes.application.usecase;

import com.microservice.mscclientes.domain.entity.Client;
import com.microservice.mscclientes.domain.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Use case for creating a new client.
 * Implements business logic for client creation.
 */
@Service
@Transactional
public class CreateClientUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(CreateClientUseCase.class);
    
    private final ClientRepository clientRepository;
    
    public CreateClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    /**
     * Execute the create client use case
     * @param client the client to create
     * @return the created client with generated ID
     */
    public Client execute(Client client) {
        logger.info("Creating new client: {}", client.getFullName());
        
        // Build client with timestamps
        Client clientToSave = Client.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .age(client.getAge())
                .birthDate(client.getBirthDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        Client savedClient = clientRepository.save(clientToSave);
        
        logger.info("Client created successfully with ID: {}", savedClient.getId());
        return savedClient;
    }
}
