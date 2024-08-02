package com.tinqinacademy.hotel.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BedSize {
    SINGLE("single", 1),
    DOUBLE("double", 2),
    SMALL_DOUBLE("smallDouble", 2),
    KING_SIZE("kingSize", 2),
    QUEEN_SIZE("queenSize", 2),
    UNKNOWN("", 0);

    private final String code;
    private final Integer capacity;

    BedSize(String code, Integer capacity) {
        this.code = code;
        this.capacity = capacity;
    }

    @JsonCreator
    public static BedSize getBedSize(String code) {
        return Arrays.stream(BedSize.values())
                .filter(bedSize -> bedSize.code.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
//        for (BedSize bedSize : BedSize.values()) {
//            if (bedSize.code.equals(code)) {
//                return bedSize;
//            }
//        }
//        return UNKNOWN;
    }

    @Override
    @JsonValue
    public String toString() {
        return code;
    }
}
