package com.tinqinacademy.hotel.persistence.models;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Guest {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthday;
    private String civilNumber;
    private String idIssueAuthority;
    private LocalDate idIssueDate;
    private LocalDate idValidity;
}
