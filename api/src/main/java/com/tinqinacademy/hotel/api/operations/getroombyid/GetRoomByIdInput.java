package com.tinqinacademy.hotel.api.operations.getroombyid;

import com.tinqinacademy.hotel.api.contracts.base.OperationInput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRoomByIdInput implements OperationInput {
    @Schema(example = "15")
    private String roomId;
}
