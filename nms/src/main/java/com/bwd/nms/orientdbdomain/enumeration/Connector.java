package com.bwd.nms.orientdbdomain.enumeration;

import java.util.Arrays;

/**
 * The Connector enumeration.
 */
public enum Connector {
    LCU, SCA, SCU , UNKNOWN;

	/**
     * @return the Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static Connector fromString(String s) throws IllegalArgumentException {
        return Arrays.stream(Connector.values())
                .filter(v -> v.toString().equals(s))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
