package com.exam.portal.dto;

public record RegisterRequest (
    String email,
    String name,
    String password
) { }
