package com.tinqinacademy.hotel.api.operations.createroom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CreateRoomOutput {
    private UUID roomId;
}
