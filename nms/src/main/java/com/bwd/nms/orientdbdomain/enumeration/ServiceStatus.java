package com.bwd.nms.orientdbdomain.enumeration;

import java.util.Arrays;

/**
 * The ServiceStatus enumeration.
 */
public enum ServiceStatus {
    PROVISIONING_IN_PROGRESS, TESTING_PERIOD, IN_SERVICE, DECOMMISIONED, UNKNOWN;

	/**
     * @return the Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static ServiceStatus fromString(String s) throws IllegalArgumentException {
        return Arrays.stream(ServiceStatus.values())
                .filter(v -> v.toString().equals(s))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
