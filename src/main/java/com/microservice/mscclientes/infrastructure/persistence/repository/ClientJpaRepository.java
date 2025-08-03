package com.microservice.mscclientes.infrastructure.persistence.repository;

import com.microservice.mscclientes.infrastructure.persistence.entity.ClientJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ClientJpaEntity.
 * Provides basic CRUD operations and custom queries.
 */
@Repository
public interface ClientJpaRepository extends JpaRepository<ClientJpaEntity, Long> {
    
    /**
     * Calculate average age of all clients
     * @return average age or 0 if no clients exist
     */
    @Query("SELECT COALESCE(AVG(c.age), 0.0) FROM ClientJpaEntity c")
    Double calculateAverageAge();
    
    /**
     * Calculate standard deviation of ages
     * @return standard deviation or 0 if no clients exist
     */
    @Query("SELECT COALESCE(SQRT(AVG(c.age * c.age) - AVG(c.age) * AVG(c.age)), 0.0) FROM ClientJpaEntity c")
    Double calculateAgeStandardDeviation();
}
