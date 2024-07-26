package com.tinqinacademy.hotel.api.operations.getuser;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetUserOutput {
    private String username;
    private String password;
    private LocalDate birthday;
    private String email;
    private String phone;
}
