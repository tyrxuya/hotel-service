package com.tinqinacademy.hotel.api.operations.updateroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UpdateRoomOutput {
    @Schema(example = "15")
    private UUID roomId;
}
