package com.beprime.testIntegration;


import com.beprime.persistance.dao.CategoryDao;
import com.beprime.persistance.entities.Category;
import com.beprime.persistance.entities.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static com.beprime.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ProductIT extends ITConfig {

    private static final String PRODUCT_URL = "/api/product/";
    private static final String IMAGE_URL = "/api/image/";
    private static final String SITE_STOCK_URL = "/api/siteStock/";
    private HttpHeaders httpHeaders;
    private Product product;
    private Category category;
    @Autowired
    CategoryDao categoryDao;

    @BeforeAll
    void setUp() {
        httpHeaders = getHttpHeaders();
        product = new Product(ONE, "tv32", "tv 32 pouces", 1000, 1200, true, true);
        ;
        category = new Category(1L, "tochiba", "tochiba");
    }

    @Order(1)
    @Test
    void saveIT() {
        HttpEntity entity = new HttpEntity(product, httpHeaders);

        Category categorySaved = categoryDao.saveAndFlush(category);
        product.setCategory(categorySaved);
        ResponseEntity<Product> productDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + PRODUCT_URL,
                HttpMethod.POST, entity, Product.class);
        assertNotNull(productDtoResponseEntity);
        assertEquals(HttpStatus.OK, productDtoResponseEntity.getStatusCode());
    }


    @Order(2)
    @Test
    void getProductIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<Product> productDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + PRODUCT_URL + ONE,
                HttpMethod.GET, entity, Product.class);

        assertNotNull(productDtoResponseEntity);
        assertEquals(HttpStatus.OK, productDtoResponseEntity.getStatusCode());
    }

    @Order(3)
    @Test
    void getAllProductsIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<List<Product>> responseEntity = this.getTestRestTemplate().exchange(
                getRootUrl().concat(PRODUCT_URL + "products"), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<Product>>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1L, responseEntity.getBody().get(0).getId());
    }

    @Test
    @Order(4)
    public void deleteProductIT() {
        HttpEntity entity = new HttpEntity(product, httpHeaders);
        ResponseEntity<Boolean> ResponseEntity = getTestRestTemplate().exchange(getRootUrl() + PRODUCT_URL + "delete/" + ONE,
                HttpMethod.DELETE, entity, Boolean.class);
        assertNotNull(ResponseEntity);
        assertEquals(HttpStatus.OK, ResponseEntity.getStatusCode());
    }
}