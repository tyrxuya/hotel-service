package com.tinqinacademy.hotel.api.operations.getuser;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetUserInput {
    private String userId;
}
