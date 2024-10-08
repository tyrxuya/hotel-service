package com.tinqinacademy.hotel.api.operations.getroombyid;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import com.tinqinacademy.hotel.api.enums.BathroomType;
import com.tinqinacademy.hotel.api.enums.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class GetRoomByIdOutput implements OperationOutput {
    @Schema(example = "15")
    private String roomId;

    @Schema(example = "15124.15")
    private BigDecimal price;

    @Schema(example = "5")
    private Integer floor;

    @Schema(example = "kingSize")
    private List<String> bedSizes;

    @Schema(example = "private")
    private String bathroomType;

    @Schema(example = "2")
    private Integer bedCount;

    private List<LocalDate> datesOccupied;
}
