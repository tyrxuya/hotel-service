package com.tinqinacademy.hotel.api.operations.createuser;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CreateUserOutput {
    private UUID userId;
}
