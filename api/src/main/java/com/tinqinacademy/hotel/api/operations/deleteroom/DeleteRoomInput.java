package com.tinqinacademy.hotel.api.operations.deleteroom;

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
public class DeleteRoomInput implements OperationInput {
    @Schema(example = "15")
    @UUID(message = "roomId must be a valid UUID")
    private String roomId;
}
