package com.tinqinacademy.hotel.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BathroomType {
    PRIVATE("private"),
    SHARED("shared"),
    UNKNOWN("");

    private final String code;

    BathroomType(String code) {
        this.code = code;
    }

    @JsonCreator
    public static BathroomType getBathroomType(String code) {
        return Arrays.stream(BathroomType.values())
                .filter(bathroomType -> bathroomType.code.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
//        for (BathroomType bathroomType : BathroomType.values()) {
//            if (bathroomType.code.equals(code)) {
//                return bathroomType;
//            }
//        }
//        return BathroomType.UNKNOWN;
    }

    @Override
    @JsonValue
    public String toString() {
        return code;
    }
}
