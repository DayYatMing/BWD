package com.bwd.nms.orientdbdomain.enumeration;

import java.util.Arrays;

/**
 * The CardType enumeration.
 */
public enum CardType {
    WSAI, MOTR, FOTR, TRANSP, MUXP, C5160, C5170, ODF_TRX, ODF_MRB, ODF_COL, ODF_MMR, ODF_DIR, ODF_MRH, UNKNOWN;

    /**
     * @return the Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static CardType fromString(String s) throws IllegalArgumentException {
        return Arrays.stream(CardType.values())
                .filter(v -> v.toString().equals(s))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
