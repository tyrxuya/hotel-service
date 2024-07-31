package com.tinqinacademy.hotel.api.operations.bookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.contracts.base.OperationInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class BookRoomInput implements OperationInput {
    @JsonIgnore
    private String roomId;

    @Schema(example = "2025-05-22")
    @FutureOrPresent(message = "invalid startDate")
    private LocalDate startDate;

    @Schema(example = "2025-05-25")
    @FutureOrPresent(message = "invalid endDate")
    private LocalDate endDate;

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

    private String username;
}
