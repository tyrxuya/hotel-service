package com.tinqinacademy.hotel.operations.updateroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UpdateRoomOutput {
    @Schema(example = "15")
    private String roomId;
}
