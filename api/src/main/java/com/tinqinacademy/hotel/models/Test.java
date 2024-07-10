package com.tinqinacademy.hotel.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Test {
    @Schema(example = "qkata staq")
    private String name;

    @Schema(example = "dvoina")
    private String type;

    @Schema(example = "103")
    private Integer roomNumber;
}
