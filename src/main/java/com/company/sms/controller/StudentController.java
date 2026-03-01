/*
 * Copyright (c) 2026 Company. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Company.
 * You shall not disclose such confidential information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Company.
 */

package com.company.sms.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.company.sms.model.Student;
import com.company.sms.service.StudentService;

/**
 * REST Controller for managing Student entities.
 * Provides CRUD operations for student management.
 * 
 * @author SMS Application
 * @version 1.0
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Get all students from the database.
     * 
     * @return List of all Student objects
     */
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * Get a student by their ID.
     * 
     * @param id The ID of the student to retrieve
     * @return ResponseEntity containing the Student if found, or 404 Not Found if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new student in the database.
     * 
     * @param student The Student object to create (validated)
     * @param result BindingResult containing validation errors if any
     * @return ResponseEntity containing the created Student with 201 Created status, 
     *         or 400 Bad Request with validation errors if validation fails
     */
    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    /**
     * Update an existing student by their ID.
     * 
     * @param id The ID of the student to update
     * @param studentDetails The updated Student object (validated)
     * @param result BindingResult containing validation errors if any
     * @return ResponseEntity containing the updated Student if found and updated,
     *         or 404 Not Found if student doesn't exist,
     *         or 400 Bad Request with validation errors if validation fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody Student studentDetails, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Optional<Student> updatedStudent = studentService.updateStudent(id, studentDetails);
        return updatedStudent.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a student by their ID.
     * 
     * @param id The ID of the student to delete
     * @return ResponseEntity with 204 No Content if deleted successfully,
     *         or 404 Not Found if student doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        boolean deleted = studentService.deleteStudent(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
