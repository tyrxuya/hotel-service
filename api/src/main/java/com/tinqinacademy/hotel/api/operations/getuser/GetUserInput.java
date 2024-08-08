package com.tinqinacademy.hotel.api.operations.getuser;

import com.tinqinacademy.hotel.api.base.OperationInput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetUserInput implements OperationInput {
    private String userId;
}
