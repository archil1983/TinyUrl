package org.neueda.quiz.config;

import com.datastax.driver.core.Session;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neueda.quiz.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Application.class)
@ActiveProfiles("IT")
public class CassandraConfigTestIT {


    @ClassRule
    public static DockerComposeContainer environment = new DockerComposeContainer(new File("docker-compose_it.yml"));


    @Autowired
    private Session session;


    @Test
    public void testConfig() {
        assertFalse(session.isClosed());
    }

}