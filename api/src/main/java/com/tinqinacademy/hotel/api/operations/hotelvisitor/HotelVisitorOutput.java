package com.tinqinacademy.hotel.api.operations.hotelvisitor;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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

    @Schema(example = "0889252012")
    private String phoneNo;

    @Schema(example = "2003-09-22")
    private LocalDate birthday;

    @Schema(example = "0349228888")
    private String civilNumber;

    @Schema(example = "mvr varna")
    private String idIssueAuthority;

    @Schema(example = "2015-05-22")
    private LocalDate idIssueDate;

    @Schema(example = "2027-12-12")
    private LocalDate idValidity;
}
