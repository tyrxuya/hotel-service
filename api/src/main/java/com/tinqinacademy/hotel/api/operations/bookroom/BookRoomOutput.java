package com.tinqinacademy.hotel.api.operations.bookroom;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BookRoomOutput implements OperationOutput {
    private String bookingId;
}
