/*
 * Copyright (c) 2026 Company. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Company.
 * You shall not disclose such confidential information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Company.
 */

package com.company.sms.service;

import com.company.sms.model.Student;
import com.company.sms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Student Service Tests")
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student testStudent1;
    private Student testStudent2;
    private List<Student> studentList;

    @BeforeEach
    void setUp() {
        testStudent1 = new Student(1L, "John", "Doe", "john.doe@example.com", "2023-001", "10A", "123 Main St");
        testStudent2 = new Student(2L, "Jane", "Smith", "jane.smith@example.com", "2023-002", "10B", "456 Oak Ave");
        studentList = Arrays.asList(testStudent1, testStudent2);
    }

    @Test
    @DisplayName("getAllStudents() - Should return list of all students")
    void getAllStudents_ShouldReturnListOfAllStudents() {
        // Given
        when(studentRepository.findAll()).thenReturn(studentList);

        // When
        List<Student> result = studentService.getAllStudents();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllStudents() - Should return empty list when no students exist")
    void getAllStudents_ShouldReturnEmptyList_WhenNoStudentsExist() {
        // Given
        when(studentRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Student> result = studentService.getAllStudents();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getStudentById() - Should return student when found")
    void getStudentById_ShouldReturnStudent_WhenStudentExists() {
        // Given
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent1));

        // When
        Optional<Student> result = studentService.getStudentById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
        assertEquals("john.doe@example.com", result.get().getEmail());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getStudentById() - Should return empty optional when student not found")
    void getStudentById_ShouldReturnEmptyOptional_WhenStudentNotFound() {
        // Given
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Student> result = studentService.getStudentById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("createStudent() - Should save and return student")
    void createStudent_ShouldSaveAndReturnStudent() {
        // Given
        Student newStudent = new Student("Alice", "Johnson", "alice.johnson@example.com", "2023-003", "10C");
        newStudent.setAddress("789 Pine St");
        Student savedStudent = new Student(3L, "Alice", "Johnson", "alice.johnson@example.com", "2023-003", "10C", "789 Pine St");
        
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        // When
        Student result = studentService.createStudent(newStudent);

        // Then
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Alice", result.getFirstName());
        assertEquals("Johnson", result.getLastName());
        assertEquals("alice.johnson@example.com", result.getEmail());
        assertEquals("2023-003", result.getRollNo());
        assertEquals("10C", result.getStudentClass());
        assertEquals("789 Pine St", result.getAddress());
        verify(studentRepository, times(1)).save(newStudent);
    }

    @Test
    @DisplayName("createStudent() - Should save student without address")
    void createStudent_ShouldSaveStudent_WithoutAddress() {
        // Given
        Student newStudent = new Student("Alice", "Johnson", "alice.johnson@example.com", "2023-003", "10C");
        Student savedStudent = new Student(3L, "Alice", "Johnson", "alice.johnson@example.com", "2023-003", "10C", null);
        
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        // When
        Student result = studentService.createStudent(newStudent);

        // Then
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Alice", result.getFirstName());
        assertNull(result.getAddress());
        verify(studentRepository, times(1)).save(newStudent);
    }

    @Test
    @DisplayName("updateStudent() - Should update and return student when found")
    void updateStudent_ShouldUpdateAndReturnStudent_WhenStudentExists() {
        // Given
        Student updatedDetails = new Student("John", "Updated", "john.updated@example.com", "2023-001", "10A");
        updatedDetails.setAddress("Updated Address");
        Student existingStudent = new Student(1L, "John", "Doe", "john.doe@example.com", "2023-001", "10A", "123 Main St");
        Student updatedStudent = new Student(1L, "John", "Updated", "john.updated@example.com", "2023-001", "10A", "Updated Address");
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        // When
        Optional<Student> result = studentService.updateStudent(1L, updatedDetails);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Updated", result.get().getLastName());
        assertEquals("john.updated@example.com", result.get().getEmail());
        assertEquals("Updated Address", result.get().getAddress());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    @DisplayName("updateStudent() - Should return empty optional when student not found")
    void updateStudent_ShouldReturnEmptyOptional_WhenStudentNotFound() {
        // Given
        Student updatedDetails = new Student("John", "Updated", "john.updated@example.com", "2023-001", "10A");
        updatedDetails.setAddress("Updated Address");
        
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Student> result = studentService.updateStudent(999L, updatedDetails);

        // Then
        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findById(999L);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("updateStudent() - Should update all fields including address to null")
    void updateStudent_ShouldUpdateAllFields_IncludingAddressToNull() {
        // Given
        Student updatedDetails = new Student("John", "Updated", "john.updated@example.com", "2023-001", "10A");
        updatedDetails.setAddress(null);
        Student existingStudent = new Student(1L, "John", "Doe", "john.doe@example.com", "2023-001", "10A", "123 Main St");
        Student updatedStudent = new Student(1L, "John", "Updated", "john.updated@example.com", "2023-001", "10A", null);
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        // When
        Optional<Student> result = studentService.updateStudent(1L, updatedDetails);

        // Then
        assertTrue(result.isPresent());
        assertNull(result.get().getAddress());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    @DisplayName("deleteStudent() - Should delete and return true when student exists")
    void deleteStudent_ShouldDeleteAndReturnTrue_WhenStudentExists() {
        // Given
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        // When
        boolean result = studentService.deleteStudent(1L);

        // Then
        assertTrue(result);
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteStudent() - Should return false when student not found")
    void deleteStudent_ShouldReturnFalse_WhenStudentNotFound() {
        // Given
        when(studentRepository.existsById(999L)).thenReturn(false);

        // When
        boolean result = studentService.deleteStudent(999L);

        // Then
        assertFalse(result);
        verify(studentRepository, times(1)).existsById(999L);
        verify(studentRepository, never()).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("updateStudent() - Should update only provided fields")
    void updateStudent_ShouldUpdateOnlyProvidedFields() {
        // Given
        Student updatedDetails = new Student();
        updatedDetails.setFirstName("John");
        updatedDetails.setEmail("john.new@example.com");
        // Other fields are null
        
        Student existingStudent = new Student(1L, "John", "Doe", "john.doe@example.com", "2023-001", "10A", "123 Main St");
        Student updatedStudent = new Student(1L, "John", "Doe", "john.new@example.com", "2023-001", "10A", "123 Main St");
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        // When
        Optional<Student> result = studentService.updateStudent(1L, updatedDetails);

        // Then
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName()); // Updated
        assertEquals("Doe", result.get().getLastName()); // Unchanged
        assertEquals("john.new@example.com", result.get().getEmail()); // Updated
        assertEquals("2023-001", result.get().getRollNo()); // Unchanged
        assertEquals("10A", result.get().getStudentClass()); // Unchanged
        assertEquals("123 Main St", result.get().getAddress()); // Unchanged
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(existingStudent);
    }
}
