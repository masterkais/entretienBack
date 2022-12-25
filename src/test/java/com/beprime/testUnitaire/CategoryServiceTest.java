package com.beprime.testUnitaire;

import com.beprime.persistance.dao.BaseRepository;
import com.beprime.persistance.dao.CategoryDao;
import com.beprime.persistance.entities.Category;
import com.beprime.service.services.implService.CategoryService;
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


public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private BaseRepository<Category, Long> baseRepository;
    @Mock
    private CategoryDao categoryDao;
    private Category category;
    private List<Category> categoryList;

    @BeforeEach
    void setup() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        MockitoAnnotations.initMocks(this);
        category = new Category(ONE, "categoryName", "description");
        categoryList = new ArrayList<Category>();
        categoryList.add(category);
        category.setProductList(null);
    }

    @AfterEach
    void tearDown() {
        category = null;
        categoryList = null;
    }

    @Test
    @DisplayName("test for save")
    public void saveCategoryTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        when(categoryDao.findByName(category.getName())).thenReturn(null);
        when(categoryDao.findAll()).thenReturn(null);
        when(categoryDao.saveAndFlush(category)).thenReturn(category);
        Category categoryDtoResult = categoryService.save(category);
        assertNotNull(category);
        assertEquals(categoryDtoResult,category);
        verify(categoryDao, times(1)).saveAndFlush(any());
    }

    @Test
    @DisplayName("test for update")
    public void updateCategoryTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        when(categoryDao.findByName(category.getName())).thenReturn(null);
        when(categoryDao.findAll()).thenReturn(null);
        when(categoryDao.findOne(anyLong())).thenReturn(category);
        when(categoryDao.saveAndFlush(category)).thenReturn(category);
        Category categoryDtoResult = categoryService.update(category);
        assertNotNull(category);
        assertEquals(categoryDtoResult, this.category);
        verify(categoryDao, times(1)).saveAndFlush(any());

    }

    @DisplayName("test for get all category")
    @Test
    public void allCategoryTest() {
        //GIVEN
        when(categoryDao.findAll()).thenReturn(categoryList);
        //WHEN
        List<Category> results = categoryService.findAllCategorie();
        //THEN
        assertNotNull(results);
        verify(categoryDao, times(1)).findAll();
    }

    @DisplayName("test for delete by id")
    @Test
    public void testDeleteCategory() {
        UUID uuid = UUID.randomUUID();
        //GIVEN
        when(categoryDao.findOne(anyLong())).thenReturn(category);
        assertDoesNotThrow(
                () -> {
                    categoryService.delete(category.getId());
                }
        );
        verify(categoryDao, times(2)).findOne(category.getId());
    }

    @DisplayName("test for get category by id")
    @Test
    public void testGetCategoryById() {
        when(categoryDao.findOne(anyLong())).thenReturn(category);
        assertDoesNotThrow(
                () -> {
                    categoryService.findById(category.getId());
                }
        );
        verify(categoryDao, times(1)).findOne(category.getId());
    }

}