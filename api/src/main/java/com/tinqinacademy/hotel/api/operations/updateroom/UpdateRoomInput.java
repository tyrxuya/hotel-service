package com.tinqinacademy.hotel.api.operations.updateroom;

import com.tinqinacademy.hotel.api.models.BathroomType;
import com.tinqinacademy.hotel.api.models.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UpdateRoomInput {
    @Schema(example = "2")
    @Min(value = 1, message = "bedCount cannot be less than 1")
    @Max(value = 10, message = "bedCount cannot be greater than 10")
    private Integer bedCount;

    @Schema(example = "kingSize")
    private BedSize bedSize;

    @Schema(example = "shared")
    private BathroomType bathroomType;

    @Schema(example = "7")
    @Min(value = 1, message = "floor cannot be less than 1")
    @Max(value = 12, message = "floor cannot be greater than 12")
    private Integer floor;

    @Schema(example = "700b")
    @NotBlank(message = "room number can't be blank")
    private String roomNo;

    @Schema(example = "1838124.15")
    //@Positive(message = "price cannot be negative")
    @DecimalMin(value = "200", message = "price cannot be lower than 200")
    @Digits(integer = 4, fraction = 2, message = "invalid price decimal")
    private BigDecimal price;
}
