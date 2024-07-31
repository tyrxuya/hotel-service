package com.tinqinacademy.hotel.api.operations.unbookroom;

import com.tinqinacademy.hotel.api.contracts.base.OperationInput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UnbookRoomInput implements OperationInput {
    @Schema(example = "100")
    private String bookingId;
}
