package com.tinqinacademy.hotel.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false, updatable = false)
    private String civilNumber;

    @Column(nullable = false)
    private String idIssueAuthority;

    @Column(nullable = false)
    private LocalDate idIssueDate;

    @Column(nullable = false)
    private LocalDate idValidity;
}
