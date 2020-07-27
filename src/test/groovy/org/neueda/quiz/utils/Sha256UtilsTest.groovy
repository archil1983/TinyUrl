package org.neueda.quiz.utils

import org.neueda.quiz.utils.Sha256Utils
import spock.lang.Specification

class Sha256UtilsTest extends Specification {

    def "Test Encode"() {
        setup:
        def a = "abced"
        def b = "abced"
        def x =  "abced "
        when:
        def c = Sha256Utils.encode(a)
        def d = Sha256Utils.encode(b)
        def e = Sha256Utils.encode(x)
        then:
        c instanceof Long
        e instanceof Long
        c == d
        e != c

    }
}
