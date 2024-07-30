package com.tinqinacademy.hotel.api.operations.deleteuser;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DeleteUserInput {
    private String userId;
}
