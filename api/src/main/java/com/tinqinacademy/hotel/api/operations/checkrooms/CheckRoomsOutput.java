package com.tinqinacademy.hotel.api.operations.checkrooms;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CheckRoomsOutput implements OperationOutput {
    private List<String> idList;
}
