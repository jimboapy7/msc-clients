package com.microservice.mscclientes.application.usecase;

import com.microservice.mscclientes.domain.repository.ClientRepository;
import com.microservice.mscclientes.domain.valueobject.ClientMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case for calculating client age metrics.
 * Implements business logic for statistical calculations.
 */
@Service
@Transactional(readOnly = true)
public class GetClientMetricsUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(GetClientMetricsUseCase.class);
    
    private final ClientRepository clientRepository;
    
    public GetClientMetricsUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    /**
     * Execute the get client metrics use case
     * @return ClientMetrics containing average age and standard deviation
     */
    public ClientMetrics execute() {
        logger.info("Calculating client age metrics");
        
        ClientMetrics metrics = clientRepository.calculateAgeMetrics();
        
        logger.info("Calculated metrics - Average: {}, StdDev: {}, Total: {}", 
                   metrics.getAverageAge(), metrics.getStandardDeviation(), metrics.getTotalClients());
        
        return metrics;
    }
}
