package com.tinqinacademy.hotel.errors;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Error {
    private String message;
    private String field;
    private String errorCode;
}
