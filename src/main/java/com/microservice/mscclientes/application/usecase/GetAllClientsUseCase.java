package com.microservice.mscclientes.application.usecase;

import com.microservice.mscclientes.domain.entity.Client;
import com.microservice.mscclientes.domain.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Use case for retrieving all clients.
 * Implements business logic for client retrieval.
 */
@Service
@Transactional(readOnly = true)
public class GetAllClientsUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(GetAllClientsUseCase.class);
    
    private final ClientRepository clientRepository;
    
    public GetAllClientsUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    /**
     * Execute the get all clients use case
     * @return list of all clients
     */
    public List<Client> execute() {
        logger.info("Retrieving all clients");
        
        List<Client> clients = clientRepository.findAll();
        
        logger.info("Retrieved {} clients", clients.size());
        return clients;
    }
}
