package com.tinqinacademy.hotel.api.operations.unbookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UnbookRoomInput implements OperationInput {
    @JsonIgnore
    @UUID(message = "bookingId must be a valid UUID")
    private String bookingId;

    @UUID(message = "userId must be a valid UUID")
    @NotBlank(message = "userId cant be blank")
    private String userId;
}
