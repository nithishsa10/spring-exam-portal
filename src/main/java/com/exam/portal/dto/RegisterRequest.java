package com.exam.portal.dto;

import org.springframework.http.HttpStatusCode;

public record RegisterRequest (
    String email,
    String name,
    String password
) { }
