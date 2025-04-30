package com.jbatrina.BankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private int userId;
}
