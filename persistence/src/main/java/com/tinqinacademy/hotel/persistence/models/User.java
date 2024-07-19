package com.tinqinacademy.hotel.persistence.models;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private LocalDate birthday;
    private String phone;
}
