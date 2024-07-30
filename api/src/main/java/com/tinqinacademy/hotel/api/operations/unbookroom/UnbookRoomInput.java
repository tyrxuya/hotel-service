package com.tinqinacademy.hotel.api.operations.unbookroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UnbookRoomInput {
    @Schema(example = "100")
    private String bookingId;
}
