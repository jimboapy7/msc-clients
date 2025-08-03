package com.microservice.mscclientes.infrastructure.persistence.repository;

import com.microservice.mscclientes.domain.entity.Client;
import com.microservice.mscclientes.domain.repository.ClientRepository;
import com.microservice.mscclientes.domain.valueobject.ClientMetrics;
import com.microservice.mscclientes.infrastructure.persistence.entity.ClientJpaEntity;
import com.microservice.mscclientes.infrastructure.persistence.mapper.ClientMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of ClientRepository using Spring Data JPA.
 * Adapts between domain and infrastructure layers.
 */
@Repository
public class ClientRepositoryImpl implements ClientRepository {
    
    private final ClientJpaRepository jpaRepository;
    private final ClientMapper mapper;
    
    public ClientRepositoryImpl(ClientJpaRepository jpaRepository, ClientMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Client save(Client client) {
        ClientJpaEntity jpaEntity = mapper.toJpaEntity(client);
        ClientJpaEntity savedEntity = jpaRepository.save(jpaEntity);
        return mapper.toDomainEntity(savedEntity);
    }
    
    @Override
    public Optional<Client> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomainEntity);
    }
    
    @Override
    public List<Client> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomainEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
    
    @Override
    public long count() {
        return jpaRepository.count();
    }
    
    @Override
    public ClientMetrics calculateAgeMetrics() {
        long totalClients = jpaRepository.count();
        
        if (totalClients == 0) {
            return new ClientMetrics(0.0, 0.0, 0);
        }
        
        Double averageAge = jpaRepository.calculateAverageAge();
        Double standardDeviation = jpaRepository.calculateAgeStandardDeviation();
        
        return new ClientMetrics(
                averageAge != null ? averageAge : 0.0,
                standardDeviation != null ? standardDeviation : 0.0,
                totalClients
        );
    }
}
