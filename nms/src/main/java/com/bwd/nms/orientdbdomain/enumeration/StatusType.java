package com.bwd.nms.orientdbdomain.enumeration;

import java.util.Arrays;

/**
 * The StatusType enumeration.
 */
public enum StatusType {
    FREE, RESERVED, ALLOCATED, DECOMMISIONED, UNKNOWN;

	/**
     * @return the Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static StatusType fromString(String s) throws IllegalArgumentException {
        return Arrays.stream(StatusType.values())
                .filter(v -> v.toString().equals(s))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
