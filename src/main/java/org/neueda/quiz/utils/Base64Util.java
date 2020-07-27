package org.neueda.quiz.utils;


import java.nio.charset.StandardCharsets;

import static org.springframework.util.Base64Utils.decodeFromString;
import static org.springframework.util.Base64Utils.encode;

public final class Base64Util {

    private Base64Util() {
    }

    public static String encodeBase64(long longId){
        return new String(encode(String.valueOf(longId).getBytes()), StandardCharsets.UTF_8);
    }

    public static long decodeBase64(String encodedId){
        return Long.parseLong(new String(decodeFromString(encodedId),StandardCharsets.UTF_8));
    }
}
