package com.tinqinacademy.hotel.api.operations.checkrooms;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CheckRoomsOutput {
    private List<String> idList;
}
