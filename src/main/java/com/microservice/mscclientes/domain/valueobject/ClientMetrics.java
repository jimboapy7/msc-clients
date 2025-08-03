package com.microservice.mscclientes.domain.valueobject;

/**
 * Value object representing client age metrics.
 * Immutable object that encapsulates statistical calculations.
 */
public class ClientMetrics {
    private final double averageAge;
    private final double standardDeviation;
    private final long totalClients;

    public ClientMetrics(double averageAge, double standardDeviation, long totalClients) {
        this.averageAge = averageAge;
        this.standardDeviation = standardDeviation;
        this.totalClients = totalClients;
    }

    public double getAverageAge() {
        return averageAge;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public long getTotalClients() {
        return totalClients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientMetrics that = (ClientMetrics) o;
        return Double.compare(that.averageAge, averageAge) == 0 &&
               Double.compare(that.standardDeviation, standardDeviation) == 0 &&
               totalClients == that.totalClients;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(averageAge, standardDeviation, totalClients);
    }

    @Override
    public String toString() {
        return "ClientMetrics{" +
                "averageAge=" + averageAge +
                ", standardDeviation=" + standardDeviation +
                ", totalClients=" + totalClients +
                '}';
    }
}
