package com.tinqinacademy.hotel.api.operations.checkrooms;

import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.validators.bathroomtype.ValidBathroomType;
import com.tinqinacademy.hotel.api.validators.bedsize.ValidBedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CheckRoomsInput implements OperationInput {
    @Schema(example = "2022-05-22")
    private LocalDate startDate;

    @Schema(example = "2022-05-25")
    private LocalDate endDate;

    @Schema(example = "kingSize")
    @ValidBedSize(optional = true)
    private String bedSize;

    @Schema(example = "2")
    @Min(value = 1, message = "bed count must be a positive number")
    @Max(value = 10, message = "bed count must not exceed 10")
    private Integer bedCount;

    @Schema(example = "private")
    @ValidBathroomType(optional = true)
    private String bathroomType;
}
