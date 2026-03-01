/*
 * Copyright (c) 2026 Company. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Company.
 * You shall not disclose such confidential information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Company.
 */

package com.company.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.sms.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
