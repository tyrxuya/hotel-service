package com.tinqinacademy.hotel.api.operations.deleteroom;

import com.tinqinacademy.hotel.api.contracts.base.OperationInput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DeleteRoomInput implements OperationInput {
    @Schema(example = "15")
    private String roomId;
}
