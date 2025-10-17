package org.example.sigaut_backend.controller.auth.dto;

import lombok.Value;

@Value
public class SignedDto {
    String token;
    String tokenType;
    SimpleUserDto user;
}
