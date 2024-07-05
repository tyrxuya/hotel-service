package com.tinqinacademy.hotel.operations.bookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class BookRoomInput {
    @JsonIgnore
    private String roomId;

    @Schema(example = "2022-05-22")
    private LocalDate startDate;

    @Schema(example = "2022-05-25")
    private LocalDate endDate;

    @Schema(example = "vanio")
    private String firstName;

    @Schema(example = "georgiev")
    private String lastName;

    @Schema(example = "+359889252012")
    private String phoneNo;
}
