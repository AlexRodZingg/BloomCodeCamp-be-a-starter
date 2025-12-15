package com.hcc.services;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.enums.AssignmentStatusEnum;
import com.hcc.exceptions.ResourceNotFoundException;
import com.hcc.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Service layer for managing {@link Assignment} entities
 *
 * This class contains business logic related to assignments, including:
 *      Retrieving assignments for  a specific user
 *      Creating new assignments with default values
 *      Determining the next sequential assignment number per user
 *      Updating and deleting assignments
 *
 * Acts as an intermediary between controllers and the data access layer,
 * keeping controllers thin and centralizing domain logic.
 */
@Service
public class AssignmentService {
    @Autowired
    AssignmentRepository assignmentRepository;

    public Set<Assignment> findByUser(User user) {
        return assignmentRepository.findByUser(user);
    }

    public Assignment save(User user) {
        Assignment assignment = new Assignment();
        assignment.setUser(user);
        assignment.setStatus(AssignmentStatusEnum.PENDING_SUBMISSION.getStatus());
        assignment.setNumber(findNextAssignmentToSubmit(user));
        return assignmentRepository.save(assignment);
    }

    private Integer findNextAssignmentToSubmit(User user) {
        Set<Assignment> assignmentsByUser = assignmentRepository.findByUser(user);
        if (assignmentsByUser == null)
            return 1;

        Optional<Integer> sortedAssignment = assignmentsByUser.stream()
                .sorted((a1, a2) -> {
                    if (a1.getNumber() == null) return 1;
                    if (a2.getNumber() == null) return 1;
                    return a2.getNumber().compareTo(a1.getNumber());
                }).map(assignment -> {
                    if (assignment.getNumber() == null) return 1;
                    return assignment.getNumber() + 1;
                }).findFirst();
        return sortedAssignment.orElse(1);
    }

    public Optional<Assignment> findById(Long id) {
        return assignmentRepository.findById(id);
    }

    public Assignment save(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public ResponseEntity<?> delete(Long id) {
        Assignment assignmentToDelete = assignmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Assignment does not exist with id " + id));
        assignmentRepository.delete(assignmentToDelete);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
