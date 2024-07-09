package com.tinqinacademy.hotel.errorhandler;

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
