package com.tinqinacademy.hotel.api.operations.updateroom;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UpdateRoomOutput implements OperationOutput {
    @Schema(example = "15")
    private String roomId;
}
