package com.bwd.nms.domain;

import java.util.Arrays;

public enum Feature {

	SITE, SEGMENT , OFFNET  , BACKHAUL , ROUTE , ROOMLOCATION , CARD , PORT , UNKNOWN ;

	/**
     * @return the Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static Feature fromString(String s) throws IllegalArgumentException {
        return Arrays.stream(Feature.values())
                .filter(v -> v.toString().equals(s))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
