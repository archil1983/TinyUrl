package org.neueda.quiz.dao

import com.datastax.driver.core.ResultSet
import com.datastax.driver.core.Row
import com.datastax.driver.core.Session
import org.neueda.quiz.constants.TinyUrlTableEnum
import org.springframework.core.env.Environment
import spock.lang.Specification

class TinyUrlCassandraDaoTest extends Specification {
    def "GetRecordById"() {
        setup:
        def session = Mock(Session)
        def env = Mock(Environment)
        def tinyUrlDao = new TinyUrlCassandraDao(session, env)
        def idValue = "idValue"
        def rs = Mock(ResultSet)
        def row = Mock(Row)
        when:
        def retRecord = tinyUrlDao.getRecordById(idValue, TinyUrlTableEnum.FULL_URL, TinyUrlTableEnum.ID)
        then:
        1 * session.execute(_) >> rs
        1 * rs.isExhausted() >> false
        1 * rs.one() >> row
        1 * row.getString(TinyUrlTableEnum.FULL_URL.getColumnName()) >> "FULL_URL"
        1 * row.getString(TinyUrlTableEnum.ID.getColumnName()) >> "ID"
        retRecord.size() == 2
        retRecord.get(TinyUrlTableEnum.ID) == "ID"
        retRecord.get(TinyUrlTableEnum.FULL_URL) == "FULL_URL"

    }

    def "InsertRow"() {
        setup:
        Map<TinyUrlTableEnum, String> row = new HashMap<>()
        row.put(TinyUrlTableEnum.ID, "ID")
        row.put(TinyUrlTableEnum.FULL_URL, "FULL_URL")
        def session = Mock(Session)
        def env = Mock(Environment)
        def tinyUrlDao = new TinyUrlCassandraDao(session, env)
        def idValue = "idValue"
        def rs = Mock(ResultSet)
        when:
        tinyUrlDao.insertRow(row)
        then:
        1 * env.getRequiredProperty("cassandra.keyspace") >> "cassandra.keyspace"
        1 * env.getRequiredProperty("cassandra.table") >> "cassandra.table"
        1 * env.getRequiredProperty("cassandra.ttl", Integer.class) >> 10
        1* session.execute(_)

    }
}
