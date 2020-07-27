package org.neueda.quiz.utils

import spock.lang.Specification

class Base64UtilTest extends Specification {

    def "DecodeBase64 && EncodeBase64"() {
        setup:
        def timeId = System.currentTimeMillis();
        def a = Base64Util.encodeBase64(timeId)
        def b = Base64Util.encodeBase64(timeId)
        when:
        def c = Base64Util.encodeBase64(timeId+1L)
        then:
        a == b
        c != a
        timeId == Base64Util.decodeBase64(a)
    }
}
