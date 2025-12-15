package com.hcc.controllers;

import com.hcc.dto.AssignmentResponseDto;
import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.services.AssignmentService;
import com.hcc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Assignment resources.
 *
 * This controller exposes authenticated CRUD endpoints for assignments.
 * All operations are scoped to the currently authenticated user, which
 * is automatically injected via {@link org.springframework.security.core.annotation.AuthenticationPrincipal}
 *
 * Base path: {@code /api/assignments}
 *
 * Supported operations:
 * Retrieve all assignments for the authenticated user
 * Retrieve a single assignment by ID
 * Create a new assignment for the authenticated user
 * Update an existing assignment
 * Delete an assignment by ID
 */
@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAssignments(@AuthenticationPrincipal User user) {
        Set<Assignment> assignmentsByUser = assignmentService.findByUser(user);
        return ResponseEntity.ok(assignmentsByUser);
    }

    @GetMapping("{assignmentId}")
    public ResponseEntity<?> getAssignments(@PathVariable Long assignmentId, @AuthenticationPrincipal User user) {
        Optional<Assignment> assignmentOpt = assignmentService.findById(assignmentId);

        return ResponseEntity.ok(new AssignmentResponseDto(assignmentOpt.orElse(new Assignment())));
    }

    @PutMapping("{assignmentId}")
    public ResponseEntity<?> updateAssignment(@PathVariable Long assignmentId, @RequestBody Assignment assignment, @AuthenticationPrincipal User user) {
        Assignment updatedAssignment = assignmentService.save(assignment);
        return ResponseEntity.ok(updatedAssignment);
    }

    @DeleteMapping("{assignmentId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long assignmentId, @AuthenticationPrincipal User user) {
        return assignmentService.delete(assignmentId);
    }

    @PostMapping
    public ResponseEntity<?> createAssignments(@AuthenticationPrincipal User user) {
        Assignment newAssignment = assignmentService.save(user);
        return ResponseEntity.ok(newAssignment);
    }
}
