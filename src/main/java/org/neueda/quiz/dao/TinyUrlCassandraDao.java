package org.neueda.quiz.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.neueda.quiz.constants.TinyUrlTableEnum;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

@Component
public class TinyUrlCassandraDao implements TinyUrlDao {

    private final Session session;
    private final Environment env;

    public TinyUrlCassandraDao(Session session, Environment env) {
        this.session = session;
        this.env = env;
    }


    @Override
    public Map<TinyUrlTableEnum, String> getRecordById(String idValue, TinyUrlTableEnum... columns) {
        final String[] columnNames = Arrays.stream(columns).map(TinyUrlTableEnum::getColumnName).toArray(String[]::new);
        final ResultSet rs = session.execute(QueryBuilder
                .select(columnNames)
                .from(env.getRequiredProperty("cassandra.keyspace"), env.getRequiredProperty("cassandra.table"))
                .where(eq(TinyUrlTableEnum.ID.getColumnName(), idValue)));
        final Map<TinyUrlTableEnum, String> result = new EnumMap<>(TinyUrlTableEnum.class);
        if (rs.isExhausted()) {
            return result;
        }
        final Row row = rs.one();
        Arrays.stream(columns).forEach(i -> result.put(i, row.getString(i.getColumnName())));
        return result;
    }

    @Override
    public void insertRow(Map<TinyUrlTableEnum, String> row) {
        final Insert insertQuery = QueryBuilder
                .insertInto(env.getRequiredProperty("cassandra.keyspace"), env.getRequiredProperty("cassandra.table"));

        row.forEach((key, value) -> insertQuery.value(key.getColumnName(), value));
        insertQuery.using(QueryBuilder.ttl(env.getRequiredProperty("cassandra.ttl", Integer.class)));
        session.execute(insertQuery);
    }
}
