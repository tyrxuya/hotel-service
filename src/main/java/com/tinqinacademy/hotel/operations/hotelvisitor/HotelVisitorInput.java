package com.tinqinacademy.hotel.operations.hotelvisitor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class HotelVisitorInput {
    @Schema(example = "2022-05-22")
    private LocalDate startDate;

    @Schema(example = "2022-05-25")
    private LocalDate endDate;

    @Schema(example = "vanio")
    private String firstName;

    @Schema(example = "georgiev")
    private String lastName;

    @Schema(example = "+359889272828")
    private String phoneNo;

    @Schema(example = "0349888888")
    private String idNo;

    @Schema(example = "mvr varna")
    private String idIssueAuthority;

    @Schema(example = "2015-05-22")
    private LocalDate idIssueDate;
}
