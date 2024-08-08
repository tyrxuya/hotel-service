package com.tinqinacademy.hotel.api.operations.updateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.validators.bathroomtype.ValidBathroomType;
import com.tinqinacademy.hotel.api.validators.bedsize.ValidBedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class UpdateRoomInput implements OperationInput {
    @JsonIgnore
    private String roomId;

    private List<@ValidBedSize(optional = true) String> bedSizes;

    @Schema(example = "shared")
    @ValidBathroomType(optional = true)
    private String bathroomType;

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
