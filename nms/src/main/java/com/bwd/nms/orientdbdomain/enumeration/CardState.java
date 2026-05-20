package com.bwd.nms.orientdbdomain.enumeration;

import java.util.Arrays;

public enum CardState {
	WORKING, FAILED , UNKNOWN;

	/**
     * @return the Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static CardState fromString(String s) throws IllegalArgumentException {
        return Arrays.stream(CardState.values())
                .filter(v -> v.toString().equals(s))
                .findFirst()
                .orElse(UNKNOWN);
             //   .orElseThrow(() -> new IllegalArgumentException("unknown value: " + s));
    }
}
