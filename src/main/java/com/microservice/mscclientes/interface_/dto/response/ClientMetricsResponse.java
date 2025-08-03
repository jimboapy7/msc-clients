package com.microservice.mscclientes.interface_.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for client metrics response data.
 * Contains statistical information about client ages.
 */
@Schema(description = "Response DTO containing client age metrics")
public class ClientMetricsResponse {
    
    @Schema(description = "Average age of all clients", example = "35.5")
    private double averageAge;
    
    @Schema(description = "Standard deviation of client ages", example = "12.3")
    private double standardDeviation;
    
    @Schema(description = "Total number of clients", example = "150")
    private long totalClients;
    
    // Default constructor
    public ClientMetricsResponse() {}
    
    // Constructor with all fields
    public ClientMetricsResponse(double averageAge, double standardDeviation, long totalClients) {
        this.averageAge = averageAge;
        this.standardDeviation = standardDeviation;
        this.totalClients = totalClients;
    }
    
    // Getters and Setters
    public double getAverageAge() {
        return averageAge;
    }
    
    public void setAverageAge(double averageAge) {
        this.averageAge = averageAge;
    }
    
    public double getStandardDeviation() {
        return standardDeviation;
    }
    
    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
    
    public long getTotalClients() {
        return totalClients;
    }
    
    public void setTotalClients(long totalClients) {
        this.totalClients = totalClients;
    }
    
    @Override
    public String toString() {
        return "ClientMetricsResponse{" +
                "averageAge=" + averageAge +
                ", standardDeviation=" + standardDeviation +
                ", totalClients=" + totalClients +
                '}';
    }
}
