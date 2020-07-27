package org.neueda.quiz.controller;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.neueda.quiz.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("IT")
public class TinyUrlControllerTestIT {

    private static final String TEST_URL ="https://www.bbc.com/news/av/uk-england-merseyside-46802068/students-journey-5500-miles-to-thank-korean-war-vets";
    private static final String TINY_URL_PATH ="LTUxNjcyMzY2MDE3Njg2Mzc1Nzk=";

    @LocalServerPort
    private int port;

    private String baseUrl;


    @Before
    public void init() {
        baseUrl = "http://localhost:" + port + "/api/v1";
    }

    @ClassRule
    public static DockerComposeContainer environment = new DockerComposeContainer(new File("docker-compose_it.yml"));

    @Test
    public void redirectUrlAndExposeUrl() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/"+TINY_URL_PATH, String.class);
        assertEquals("The request was redirected ", 302, response.getStatusCodeValue());
        response = restTemplate.getForEntity(baseUrl + "/origin_url/"+TINY_URL_PATH, String.class);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(TEST_URL,response.getBody());
    }

    @Test
    public void createTinyUrlPath() {
        final   RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();

        final ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/", new HttpEntity<>(TEST_URL, headers), String.class);
        assertEquals("new record created", 200, response.getStatusCodeValue());
        assertEquals(TINY_URL_PATH,response.getBody());
        try {
            restTemplate.postForEntity(baseUrl + "/", new HttpEntity<>("", headers), Void.class);
        } catch (HttpClientErrorException e) {
            assertEquals("the message body must not be empty  ", 400, e.getStatusCode().value());
        }
    }
}