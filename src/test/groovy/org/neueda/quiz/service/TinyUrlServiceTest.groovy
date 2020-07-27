package org.neueda.quiz.service

import org.neueda.quiz.constants.TinyUrlTableEnum
import org.neueda.quiz.dao.TinyUrlDao
import org.neueda.quiz.exception.TinyUrlNotFoundException
import org.neueda.quiz.utils.Base64Util
import spock.lang.Specification

import static org.neueda.quiz.constants.TinyUrlTableEnum.FULL_URL

class TinyUrlServiceTest extends Specification {

    def "GetOriginalUrl"() {
        setup:
        TinyUrlDao tinyUrlDao = Mock(TinyUrlDao)
        TinyUrlService TinyUrlService = new TinyUrlService(tinyUrlDao)
        long longId = Base64Util.decodeBase64("MTIz")
        Map<TinyUrlTableEnum, String> positiveRecordRow = new EnumMap<>(TinyUrlTableEnum.class)
        positiveRecordRow.put(FULL_URL,"FULL_URL")

        when:
        String out = TinyUrlService.getOriginalUrl("MTIz")
        then:
        1 * tinyUrlDao.getRecordById(String.valueOf(longId), FULL_URL) >> positiveRecordRow
        out == positiveRecordRow.get(FULL_URL)

        when:
        TinyUrlService.getOriginalUrl("MTIz")
        then:
        1 * tinyUrlDao.getRecordById(String.valueOf(longId), FULL_URL) >> new EnumMap<>(TinyUrlTableEnum.class)
        thrown(TinyUrlNotFoundException.class)

    }

}
