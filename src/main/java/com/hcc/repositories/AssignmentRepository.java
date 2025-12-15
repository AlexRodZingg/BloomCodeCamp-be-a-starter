package com.hcc.repositories;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repository interface for managing {@link Assignment} entities.
 *
 * Extends {@link org.springframework.data.jpa.repository.JpaRepository} to provide
 * standard CRUD operations, pagination, and sorting functionality.
 *
 * Also defines custom query methods using Spring Data JPA's method name conventions.
 * Implementations are generated automatically at runtime.
 */
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Set<Assignment> findByUser(User user);
}
