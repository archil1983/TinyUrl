package org.neueda.quiz.config;


import com.datastax.driver.core.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;

import java.util.Objects;

@Configuration
public class CassandraConfig {

    private final Environment env;

    public CassandraConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(Objects.requireNonNull(env.getProperty("cassandra.contactPoints")));
        cluster.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("cassandra.port"))));
        return cluster;
    }


    @Bean
    public Session session() {
        final Session result = Objects.requireNonNull(cluster().getObject()).connect();
        result.execute(Objects.requireNonNull(env.getProperty("cassandra.create.keyspace")));
        result.execute(Objects.requireNonNull(env.getProperty("cassandra.create.table")));
        return result;
    }
}
