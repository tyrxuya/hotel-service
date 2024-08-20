package com.tinqinacademy.hotel.api.operations.unbookroom;

import com.tinqinacademy.hotel.api.base.OperationInput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UnbookRoomInput implements OperationInput {
    @Schema(example = "100")
    @UUID(message = "bookingId must be a valid UUID")
    private String bookingId;
}
