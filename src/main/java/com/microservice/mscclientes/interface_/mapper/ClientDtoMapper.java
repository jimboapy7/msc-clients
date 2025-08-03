package com.microservice.mscclientes.interface_.mapper;

import com.microservice.mscclientes.domain.entity.Client;
import com.microservice.mscclientes.domain.valueobject.ClientMetrics;
import com.microservice.mscclientes.interface_.dto.request.CreateClientRequest;
import com.microservice.mscclientes.interface_.dto.response.ClientMetricsResponse;
import com.microservice.mscclientes.interface_.dto.response.ClientResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper between DTOs and domain entities.
 * Implements the Adapter pattern for interface layer.
 */
@Component
public class ClientDtoMapper {
    
    /**
     * Convert CreateClientRequest DTO to domain Client entity
     * @param request the request DTO
     * @return domain Client entity
     */
    public Client toDomainEntity(CreateClientRequest request) {
        if (request == null) {
            return null;
        }
        
        return Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .age(request.getAge())
                .birthDate(request.getBirthDate())
                .build();
    }
    
    /**
     * Convert domain Client entity to ClientResponse DTO
     * @param client domain entity
     * @return response DTO
     */
    public ClientResponse toResponseDto(Client client) {
        if (client == null) {
            return null;
        }
        
        return new ClientResponse(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getAge(),
                client.getBirthDate(),
                client.calculateEstimatedDeathDate(),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }
    
    /**
     * Convert list of domain Client entities to list of ClientResponse DTOs
     * @param clients list of domain entities
     * @return list of response DTOs
     */
    public List<ClientResponse> toResponseDtoList(List<Client> clients) {
        if (clients == null) {
            return null;
        }
        
        return clients.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Convert domain ClientMetrics to ClientMetricsResponse DTO
     * @param metrics domain value object
     * @return response DTO
     */
    public ClientMetricsResponse toMetricsResponseDto(ClientMetrics metrics) {
        if (metrics == null) {
            return null;
        }
        
        return new ClientMetricsResponse(
                metrics.getAverageAge(),
                metrics.getStandardDeviation(),
                metrics.getTotalClients()
        );
    }
}
