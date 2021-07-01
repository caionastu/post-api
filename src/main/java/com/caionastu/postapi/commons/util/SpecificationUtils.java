package com.caionastu.postapi.commons.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpecificationUtils {

    public static final String WILDCARD = "%";

    public static String likeFormat(String value) {
        return WILDCARD + value + WILDCARD;
    }
}
