package com.microservice.mscclientes.interface_.controller;

import com.microservice.mscclientes.application.usecase.CreateClientUseCase;
import com.microservice.mscclientes.application.usecase.GetAllClientsUseCase;
import com.microservice.mscclientes.application.usecase.GetClientMetricsUseCase;
import com.microservice.mscclientes.domain.entity.Client;
import com.microservice.mscclientes.domain.valueobject.ClientMetrics;
import com.microservice.mscclientes.interface_.dto.request.CreateClientRequest;
import com.microservice.mscclientes.interface_.dto.response.ClientMetricsResponse;
import com.microservice.mscclientes.interface_.dto.response.ClientResponse;
import com.microservice.mscclientes.interface_.mapper.ClientDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for client management operations.
 * Handles CRUD operations and metrics calculations for clients.
 */
@RestController
@RequestMapping("/clients")
@Tag(name = "Clients", description = "Client management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ClientController {
    
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    
    private final CreateClientUseCase createClientUseCase;
    private final GetAllClientsUseCase getAllClientsUseCase;
    private final GetClientMetricsUseCase getClientMetricsUseCase;
    private final ClientDtoMapper dtoMapper;
    
    public ClientController(CreateClientUseCase createClientUseCase,
                           GetAllClientsUseCase getAllClientsUseCase,
                           GetClientMetricsUseCase getClientMetricsUseCase,
                           ClientDtoMapper dtoMapper) {
        this.createClientUseCase = createClientUseCase;
        this.getAllClientsUseCase = getAllClientsUseCase;
        this.getClientMetricsUseCase = getClientMetricsUseCase;
        this.dtoMapper = dtoMapper;
    }
    
    @PostMapping
    @Operation(
        summary = "Create a new client",
        description = "Creates a new client with the provided information. Validates input data and returns the created client."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Client created successfully",
            content = @Content(schema = @Schema(implementation = ClientResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - JWT token required",
            content = @Content(schema = @Schema(implementation = String.class))
        ),
        @ApiResponse(
            responseCode = "422",
            description = "Validation error - Invalid input data",
            content = @Content(schema = @Schema(implementation = String.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = String.class))
        )
    })
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody CreateClientRequest request) {
        logger.info("Creating new client: {} {}", request.getFirstName(), request.getLastName());
        
        try {
            Client domainClient = dtoMapper.toDomainEntity(request);
            Client createdClient = createClientUseCase.execute(domainClient);
            ClientResponse response = dtoMapper.toResponseDto(createdClient);
            
            logger.info("Client created successfully with ID: {}", createdClient.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            logger.error("Validation error creating client: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            logger.error("Error creating client", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping
    @Operation(
        summary = "Get all clients",
        description = "Retrieves all clients with complete information including estimated death date based on life expectancy."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Clients retrieved successfully",
            content = @Content(schema = @Schema(implementation = ClientResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - JWT token required",
            content = @Content(schema = @Schema(implementation = String.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = String.class))
        )
    })
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        logger.info("Retrieving all clients");
        
        try {
            List<Client> clients = getAllClientsUseCase.execute();
            List<ClientResponse> response = dtoMapper.toResponseDtoList(clients);
            
            logger.info("Retrieved {} clients successfully", clients.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error retrieving clients", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/metrics")
    @Operation(
        summary = "Get client age metrics",
        description = "Calculates and returns statistical metrics about client ages including average and standard deviation. Handles the case when no clients exist."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Metrics calculated successfully",
            content = @Content(schema = @Schema(implementation = ClientMetricsResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - JWT token required",
            content = @Content(schema = @Schema(implementation = String.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = String.class))
        )
    })
    public ResponseEntity<ClientMetricsResponse> getClientMetrics() {
        logger.info("Calculating client metrics");
        
        try {
            ClientMetrics metrics = getClientMetricsUseCase.execute();
            ClientMetricsResponse response = dtoMapper.toMetricsResponseDto(metrics);
            
            logger.info("Client metrics calculated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error calculating client metrics", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
