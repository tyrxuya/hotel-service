package com.tinqinacademy.hotel.api.operations.getuser;

import com.tinqinacademy.hotel.api.contracts.base.OperationOutput;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetUserOutput implements OperationOutput {
    private String username;
    private String password;
    private LocalDate birthday;
    private String email;
    private String phone;
}
