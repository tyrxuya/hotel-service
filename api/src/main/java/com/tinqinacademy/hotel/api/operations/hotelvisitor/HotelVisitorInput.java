package com.tinqinacademy.hotel.api.operations.hotelvisitor;

import com.tinqinacademy.hotel.api.base.OperationInput;
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
public class HotelVisitorInput implements OperationInput {
    @Schema(example = "vanio")
    @NotBlank(message = "firstName cant be blank")
    @Size(min = 2, max = 50, message = "firstName must be between 2 and 50 characters")
    private String firstName;

    @Schema(example = "georgiev")
    @NotBlank(message = "lastName cant be blank")
    @Size(min = 2, max = 50, message = "lastName must be between 2 and 50 characters")
    private String lastName;

    @Schema(example = "0889252012")
    @Pattern(regexp = "^(((\\+|00)359[- ]?)|(0))(8[- ]?[789]([- ]?\\d){7})$", message = "invalid phone number")
    @NotBlank(message = "phone number cant be blank")
    @Size(min = 10, max = 13, message = "phone number must be between 10 and 13 characters")
    private String phoneNo;

    @Schema(example = "2003-09-22")
    @Past(message = "invalid birthday")
    private LocalDate birthday;

    @Schema(example = "0349228888")
    @Pattern(regexp = "\\b[0-9]{2}(?:0[1-9]|1[0-2]|2[1-9]|3[0-2]|4[1-9]|5[0-2])(?:0[1-9]|[1-2][0-9]|3[0-1])[0-9]{4}\\b", message = "invalid civil id number")
    @NotBlank(message = "card id number cannot be blank")
    @Size(min = 10, max = 10, message = "card id number must be exactly 10 characters")
    private String civilNumber;

    @Schema(example = "mvr varna")
    @NotBlank(message = "issue authority cannot be null")
    @Size(min = 5, max = 20, message = "issue authority must be between 5 and 20 characters")
    private String idIssueAuthority;

    @Schema(example = "2015-05-22")
    @PastOrPresent(message = "invalid id issue date")
    private LocalDate idIssueDate;

    @Schema(example = "2027-12-12")
    @Future(message = "invalid id validity")
    private LocalDate idValidity;
}
