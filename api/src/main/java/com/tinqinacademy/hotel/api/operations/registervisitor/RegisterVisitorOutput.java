package com.tinqinacademy.hotel.api.operations.registervisitor;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class RegisterVisitorOutput implements OperationOutput {
    private List<String> visitorIds;
}
