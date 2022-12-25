package com.beprime.testIntegration;


import com.beprime.EntretienBackApplication;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;


@Getter
@Setter
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EntretienBackApplication.class)
@TestPropertySource(locations = "classpath:bootstrap-test.properties")
@Profile("bootstrap-test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ITConfig {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    public String getRootUrl() {
        return "http://localhost:" + port;
    }

    protected HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYWlzIiwiZXhwIjoxNjcyNzkxNzQ3LCJncm91cHMiOlt7ImF1dGhvcml0eSI6IkFETUlOIn1dfQ.7NtockJgs-bBUFhF73caHOzQiM0_PE3es2_O5xHe9vs");

        return headers;
    }

}
