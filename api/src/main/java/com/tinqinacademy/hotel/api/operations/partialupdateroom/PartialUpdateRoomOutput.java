package com.tinqinacademy.hotel.api.operations.partialupdateroom;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class PartialUpdateRoomOutput implements OperationOutput {
    @Schema(example = "15")
    private String roomId;
}
