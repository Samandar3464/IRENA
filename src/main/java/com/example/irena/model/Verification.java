package com.example.irena.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Verification {
    private String email;
    private int code;

    public Verification(String phoneNumber) {
        this.email = email;
    }
}
