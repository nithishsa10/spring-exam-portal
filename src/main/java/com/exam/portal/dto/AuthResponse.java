package com.exam.portal.dto;

import lombok.Builder;

import java.time.Instant;


@Builder
public record AuthResponse(
        String authenticationToken,
        String referToken,
        Instant expiresAt,
        String username
        ) {
}
