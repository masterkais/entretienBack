package com.beprime.service.services.implService;

import com.beprime.persistance.dao.CategoryDao;
import com.beprime.persistance.entities.Category;
import com.beprime.persistance.errors.ApiErrors;
import com.beprime.service.services.interfaceService.ICategoryService;
import com.beprime.service.services.utils.errors.ErrorsResponse;
import com.beprime.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.beprime.persistance.constants.Constants.*;

@Service
@Slf4j
public class CategoryService extends GenericService<Category, Long> implements ICategoryService {
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Category save(Category category) {
        Objects.requireNonNull(category);
        checkValidName(category.getName());
        checkUniqueName(category.getName());
        Category savedCategory = categoryDao.saveAndFlush(category);
        log.info(LOG_ENTITY_CREATED, savedCategory);
        return savedCategory;
    }

    private void checkUniqueName(String name) {
        List<Category> categorys = this.categoryDao.findAll();
        int counter = 0;
        if (categorys != null) {
            for (int i = 0; i < categorys.size(); i++) {
                if (categorys.get(i).getName().equals(name)) {
                    counter++;
                }
            }
        }
        if (counter != 1 && counter != 0) {
            throw new HttpCustomException(ApiErrors.OBJECT_NAME_EXISTS, new ErrorsResponse().error(name));
        }
    }

    public void checkValidName(String name) {
        if (categoryDao.findByName(name) != null) {
            log.error(LOG_ENTITY_NAME_EXIST, name);
            throw new HttpCustomException(ApiErrors.OBJECT_NAME_EXISTS, new ErrorsResponse().error(name));
        }
    }

    @Override
    public Category update(Category category) {
        Objects.requireNonNull(category);
        checkCategoryExistByIdForUpdate(category.getId());
        checkUniqueName(category.getName());
        return categoryDao.saveAndFlush(category);
    }

    public void checkCategoryExistByIdForUpdate(Long id) {
        if (categoryDao.findOne(id) == null) {
            log.error(LOG_ENTITY_NOT_EXIST, id);
            throw new HttpCustomException(ApiErrors.OBJECT_ID_NOT_EXISTS, new ErrorsResponse().error(id));
        }
    }


    @Override
    public Category findById(Long id) {
        return Optional.ofNullable(categoryDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id)));
    }

    @Override
    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        Category categoryDto = findById(id);
        if (findById(id) != null) {
            categoryDao.delete(categoryDto.getId(), uuid);
        }
    }

    @Override
    public List<Category> findAllCategorieDispo() {
        return null;
    }

    @Override
    public Category saveCaegory(Category categoryDto) {
        return null;
    }

    @Override
    public Category updateCaegory(Category categoryDto) {
        return null;
    }

    @Override
    public List<Category> findAllCategorie() {
        List<Category> categoryList = categoryDao.findAll();
        return categoryList;
    }


}
