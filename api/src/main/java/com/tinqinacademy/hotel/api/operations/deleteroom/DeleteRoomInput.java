package com.tinqinacademy.hotel.api.operations.deleteroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DeleteRoomInput {
    @Schema(example = "15")
    private UUID roomId;
}
