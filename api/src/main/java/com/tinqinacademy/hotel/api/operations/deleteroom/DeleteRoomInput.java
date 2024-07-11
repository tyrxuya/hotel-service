package com.tinqinacademy.hotel.api.operations.deleteroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DeleteRoomInput {
    @Schema(example = "15")
    private String roomId;
}
