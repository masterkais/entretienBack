package com.beprime.testUnitaire;

import com.beprime.persistance.dao.CategoryDao;
import com.beprime.persistance.dao.ProductDao;
import com.beprime.persistance.entities.Category;
import com.beprime.persistance.entities.Product;
import com.beprime.service.services.implService.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.beprime.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductDao productDao;
    @Mock
    private CategoryDao categoryDao;
    private Product product;
    private List<Product> productList;
    private Category category;

    @BeforeEach
    void setup() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        MockitoAnnotations.initMocks(this);
        product = new Product(ONE, "tv32", "tv 32 pouces", 1000, 1200, true, true);
        ;
        category = new Category();
        product.setCategory(category);
        productList = new ArrayList<Product>();
        productList.add(product);
    }

    @AfterEach
    void tearDown() {
        productList = null;
        product = null;
    }

    @Test
    @DisplayName("test for save")
    public void saveProductTest() throws Exception {
        when(productDao.saveAndFlush(product)).thenReturn(product);
        when(categoryDao.findOne(anyLong())).thenReturn(category);
        Product productDtoSaved = productService.save(product);
        assertEquals(productDtoSaved, product);
        verify(productDao, times(1)).saveAndFlush(product);
    }

    @Test
    @DisplayName("test for update")
    public void updateCategoryTest() throws Exception {
        when(productDao.findOne(anyLong())).thenReturn(product);
        when(productDao.saveAndFlush(product)).thenReturn(product);
        when(categoryDao.findOne(anyLong())).thenReturn(category);
        Product productDtoResult = productService.findById(product.getId());
        Product productDtoSaved = productService.save(product);
        assertEquals(productDtoResult, product);
        assertEquals(productDtoSaved, product);
        assertDoesNotThrow(() -> productService.findById(product.getId()));
        verify(productDao, times(2)).findOne(product.getId());
        verify(productDao, times(1)).saveAndFlush(product);


    }

    @DisplayName("test for get all product")
    @Test
    public void allProdutTest() {
        //GIVEN
        when(productDao.findAll()).thenReturn(productList);
        //WHEN
        List<Product> results = productService.findAllSProducts();
        //THEN
        assertNotNull(results);
        verify(productDao, times(1)).findAll();
    }

    @DisplayName("test for delete by id")
    @Test
    public void testDeleteProdut() {
        UUID uuid = UUID.randomUUID();
        //GIVEN
        when(productDao.findOne(anyLong())).thenReturn(product);
        assertDoesNotThrow(
                () -> {
                    productService.delete(product.getId());
                }
        );
        verify(productDao, times(1)).findOne(product.getId());
    }


    @DisplayName("test for get category by id")
    @Test
    public void testGetProductById() {
        when(productDao.findOne(anyLong())).thenReturn(product);
        Product productDtoResult = productService.findById(product.getId());
        assertEquals(productDtoResult, product);
        assertDoesNotThrow(() -> productService.findById(product.getId()));
        verify(productDao, times(2)).findOne(product.getId());
    }

}