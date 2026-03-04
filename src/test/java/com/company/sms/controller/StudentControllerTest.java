package com.company.sms.controller;

import com.company.sms.model.Student;
import com.company.sms.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Student Controller Unit Tests")
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private StudentController studentController;

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
    @DisplayName("getAllStudents() - Should return all students")
    void getAllStudents_ShouldReturnAllStudents() {
        // Given
        when(studentService.getAllStudents()).thenReturn(studentList);

        // When
        List<Student> result = studentController.getAllStudents();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("jane.smith@example.com", result.get(1).getEmail());
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    @DisplayName("getStudentById() - Should return student when found")
    void getStudentById_ShouldReturnStudent_WhenStudentExists() {
        // Given
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(testStudent1));

        // When
        ResponseEntity<Student> result = studentController.getStudentById(1L);

        // Then
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals("John", result.getBody().getFirstName());
        assertEquals("Doe", result.getBody().getLastName());
        assertEquals("john.doe@example.com", result.getBody().getEmail());
        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    @DisplayName("getStudentById() - Should return 404 when student not found")
    void getStudentById_ShouldReturn404_WhenStudentNotFound() {
        // Given
        when(studentService.getStudentById(999L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Student> result = studentController.getStudentById(999L);

        // Then
        assertTrue(result.getStatusCode().is4xxClientError());
        assertEquals(404, result.getStatusCode().value());
        verify(studentService, times(1)).getStudentById(999L);
    }

    @Test
    @DisplayName("createStudent() - Should create new student successfully")
    void createStudent_ShouldCreateStudent_WhenValidDataProvided() {
        // Given
        Student newStudent = new Student("Alice", "Johnson", "alice.johnson@example.com", "2023-003", "10C");
        newStudent.setAddress("789 Pine St");
        Student createdStudent = new Student(3L, "Alice", "Johnson", "alice.johnson@example.com", "2023-003", "10C", "789 Pine St");
        
        when(studentService.createStudent(any(Student.class))).thenReturn(createdStudent);
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        ResponseEntity<?> result = studentController.createStudent(newStudent, bindingResult);

        // Then
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(201, result.getStatusCode().value());
        Student responseStudent = (Student) result.getBody();
        assertEquals("Alice", responseStudent.getFirstName());
        assertEquals("Johnson", responseStudent.getLastName());
        assertEquals("alice.johnson@example.com", responseStudent.getEmail());
        verify(studentService, times(1)).createStudent(any(Student.class));
    }

    @Test
    @DisplayName("updateStudent() - Should update student successfully")
    void updateStudent_ShouldUpdateStudent_WhenStudentExists() {
        // Given
        Student updatedDetails = new Student("John", "Updated", "john.updated@example.com", "2023-001", "10A");
        updatedDetails.setAddress("Updated Address");
        Student updatedStudent = new Student(1L, "John", "Updated", "john.updated@example.com", "2023-001", "10A", "Updated Address");
        
        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(Optional.of(updatedStudent));
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        ResponseEntity<?> result = studentController.updateStudent(1L, updatedDetails, bindingResult);

        // Then
        assertTrue(result.getStatusCode().is2xxSuccessful());
        Student responseStudent = (Student) result.getBody();
        assertEquals("John", responseStudent.getFirstName());
        assertEquals("Updated", responseStudent.getLastName());
        assertEquals("john.updated@example.com", responseStudent.getEmail());
        verify(studentService, times(1)).updateStudent(eq(1L), any(Student.class));
    }

    @Test
    @DisplayName("updateStudent() - Should return 404 when student not found")
    void updateStudent_ShouldReturn404_WhenStudentNotFound() {
        // Given
        Student updatedDetails = new Student("John", "Updated", "john.updated@example.com", "2023-001", "10A");
        updatedDetails.setAddress("Updated Address");
        
        when(studentService.updateStudent(eq(999L), any(Student.class))).thenReturn(Optional.empty());
        when(bindingResult.hasErrors()).thenReturn(false);

        // When
        ResponseEntity<?> result = studentController.updateStudent(999L, updatedDetails, bindingResult);

        // Then
        assertTrue(result.getStatusCode().is4xxClientError());
        assertEquals(404, result.getStatusCode().value());
        verify(studentService, times(1)).updateStudent(eq(999L), any(Student.class));
    }

    @Test
    @DisplayName("deleteStudent() - Should delete student successfully")
    void deleteStudent_ShouldDeleteStudent_WhenStudentExists() {
        // Given
        when(studentService.deleteStudent(1L)).thenReturn(true);

        // When
        ResponseEntity<Void> result = studentController.deleteStudent(1L);

        // Then
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(204, result.getStatusCode().value());
        verify(studentService, times(1)).deleteStudent(1L);
    }

    @Test
    @DisplayName("deleteStudent() - Should return 404 when student not found")
    void deleteStudent_ShouldReturn404_WhenStudentNotFound() {
        // Given
        when(studentService.deleteStudent(999L)).thenReturn(false);

        // When
        ResponseEntity<Void> result = studentController.deleteStudent(999L);

        // Then
        assertTrue(result.getStatusCode().is4xxClientError());
        assertEquals(404, result.getStatusCode().value());
        verify(studentService, times(1)).deleteStudent(999L);
    }
}
