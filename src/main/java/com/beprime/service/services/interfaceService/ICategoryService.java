package com.beprime.service.services.interfaceService;

import com.beprime.persistance.entities.Category;

import java.util.List;

public interface ICategoryService extends IGenericService<Category, Long> {
    Category save(Category category);

    Category update(Category category);

    Category findById(Long id);

    List<Category> findAllCategorie();

    void delete(Long id);

    List<Category> findAllCategorieDispo();

    Category saveCaegory(Category categoryDto);

    Category updateCaegory(Category categoryDto);
}
