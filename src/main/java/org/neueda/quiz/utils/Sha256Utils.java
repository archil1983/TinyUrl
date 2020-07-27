package org.neueda.quiz.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public final class Sha256Utils {

    private Sha256Utils() {
    }
    public static long encode(String originalString){
        return Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).asLong();

    }

}
