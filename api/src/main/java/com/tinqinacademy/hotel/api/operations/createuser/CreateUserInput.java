package com.tinqinacademy.hotel.api.operations.createuser;

import com.tinqinacademy.hotel.api.base.OperationInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CreateUserInput implements OperationInput {
    private String username;
    private String password;
    private LocalDate birthday;

    @Email
    private String email;

    @Schema(example = "0889252012")
    @Pattern(regexp = "^(((\\+|00)359[- ]?)|(0))(8[- ]?[789]([- ]?\\d){7})$", message = "invalid phone number")
    @NotBlank(message = "phone number cant be blank")
    @Size(min = 10, max = 13, message = "phone number must be between 10 and 13 characters")
    private String phone;
}
