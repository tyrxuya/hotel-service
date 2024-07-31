package com.tinqinacademy.hotel.api.operations.createuser;

import com.tinqinacademy.hotel.api.contracts.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CreateUserOutput implements OperationOutput {
    private UUID userId;
}
