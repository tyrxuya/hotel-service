package com.tinqinacademy.hotel.api.operations.checkrooms;

import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CheckRoomsOutput {
    private List<UUID> idList;
}
