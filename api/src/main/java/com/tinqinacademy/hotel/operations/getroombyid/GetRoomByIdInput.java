package com.tinqinacademy.hotel.operations.getroombyid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRoomByIdInput {
    @Schema(example = "15")
    private String roomId;
}
