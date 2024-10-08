package com.tinqinacademy.hotel.api.operations.hotelvisitor;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class HotelVisitorOutput implements OperationOutput {
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
