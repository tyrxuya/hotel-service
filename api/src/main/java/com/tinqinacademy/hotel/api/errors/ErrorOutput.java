package com.tinqinacademy.hotel.api.errors;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Component
public class ErrorOutput {
    private List<Errors> errors;
    private HttpStatus status;
}
