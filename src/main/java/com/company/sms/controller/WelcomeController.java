/*
 * Copyright (c) 2026 Company. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Company.
 * You shall not disclose such confidential information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Company.
 */

package com.company.sms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Student Management System!";
    }
}
