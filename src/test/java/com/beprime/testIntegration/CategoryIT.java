package com.beprime.testIntegration;

import com.beprime.persistance.entities.Category;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static com.beprime.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class CategoryIT extends ITConfig {

    private static final String CATEGORY_URL = "/api/category/";
    private HttpHeaders httpHeaders;
    private Category category;

    @BeforeAll
    void setUp() {
        httpHeaders = getHttpHeaders();
        category = new Category(1L, "tochiba", "tochiba");
    }

    @Order(1)
    @Test
    void saveIT() {
        HttpEntity entity = new HttpEntity(category, httpHeaders);
        ResponseEntity<Category> categoryDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + CATEGORY_URL,
                HttpMethod.POST, entity, Category.class);
        assertNotNull(categoryDtoResponseEntity);
        assertEquals(HttpStatus.OK, categoryDtoResponseEntity.getStatusCode());
    }

    @Order(2)
    @Test
    void getCategoryIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<Category> categoryDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + CATEGORY_URL + ONE,
                HttpMethod.GET, entity, Category.class);

        assertNotNull(categoryDtoResponseEntity);
        assertEquals(HttpStatus.OK, categoryDtoResponseEntity.getStatusCode());
    }

    @Order(3)
    @Test
    void getAllCategorysIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<List<Category>> responseEntity = this.getTestRestTemplate().exchange(
                getRootUrl().concat(CATEGORY_URL + "categorys"), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<Category>>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1L, responseEntity.getBody().get(0).getId());
    }

    @Test
    @Order(4)
    public void deleteAccountIT() {
        HttpEntity entity = new HttpEntity(category, httpHeaders);
        ResponseEntity<Boolean> ResponseEntity = getTestRestTemplate().exchange(getRootUrl() + CATEGORY_URL + "delete/" + ONE,
                HttpMethod.DELETE, entity, Boolean.class);
        assertNotNull(ResponseEntity);
        assertEquals(HttpStatus.OK, ResponseEntity.getStatusCode());
    }


}
