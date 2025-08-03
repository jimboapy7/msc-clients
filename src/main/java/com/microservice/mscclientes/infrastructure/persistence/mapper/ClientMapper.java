package com.microservice.mscclientes.infrastructure.persistence.mapper;

import com.microservice.mscclientes.domain.entity.Client;
import com.microservice.mscclientes.infrastructure.persistence.entity.ClientJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper between domain Client entity and JPA ClientJpaEntity.
 * Implements the Adapter pattern to convert between layers.
 */
@Component
public class ClientMapper {
    
    /**
     * Convert domain Client to JPA entity
     * @param client domain entity
     * @return JPA entity
     */
    public ClientJpaEntity toJpaEntity(Client client) {
        if (client == null) {
            return null;
        }
        
        ClientJpaEntity jpaEntity = new ClientJpaEntity();
        jpaEntity.setId(client.getId());
        jpaEntity.setFirstName(client.getFirstName());
        jpaEntity.setLastName(client.getLastName());
        jpaEntity.setAge(client.getAge());
        jpaEntity.setBirthDate(client.getBirthDate());
        jpaEntity.setCreatedAt(client.getCreatedAt());
        jpaEntity.setUpdatedAt(client.getUpdatedAt());
        
        return jpaEntity;
    }
    
    /**
     * Convert JPA entity to domain Client
     * @param jpaEntity JPA entity
     * @return domain entity
     */
    public Client toDomainEntity(ClientJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        
        return Client.builder()
                .id(jpaEntity.getId())
                .firstName(jpaEntity.getFirstName())
                .lastName(jpaEntity.getLastName())
                .age(jpaEntity.getAge())
                .birthDate(jpaEntity.getBirthDate())
                .createdAt(jpaEntity.getCreatedAt())
                .updatedAt(jpaEntity.getUpdatedAt())
                .build();
    }
}
