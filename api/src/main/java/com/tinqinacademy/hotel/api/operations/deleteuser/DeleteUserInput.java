package com.tinqinacademy.hotel.api.operations.deleteuser;

import com.tinqinacademy.hotel.api.contracts.base.OperationInput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DeleteUserInput implements OperationInput {
    private String userId;
}
