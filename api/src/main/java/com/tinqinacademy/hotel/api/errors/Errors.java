package com.tinqinacademy.hotel.api.errors;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Errors {
    private String message;
    private String field;
}
