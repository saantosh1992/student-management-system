/*
 * Copyright (c) 2026 Company. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Company.
 * You shall not disclose such confidential information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Company.
 */

package com.company.sms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @Column(nullable = false)
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Roll number is required")
    private String rollNo;
    
    @Column(nullable = false)
    @NotBlank(message = "Class is required")
    private String studentClass;
    
    @Column(length = 500)
    private String address;

    public Student() {}

    public Student(String firstName, String lastName, String email, String rollNo, String studentClass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.rollNo = rollNo;
        this.studentClass = studentClass;
    }

    public Student(Long id, String firstName, String lastName, String email, String rollNo, String studentClass, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.rollNo = rollNo;
        this.studentClass = studentClass;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
