package com.beprime.persistance.dao;

import com.beprime.persistance.entities.Category;

public interface CategoryDao extends BaseRepository<Category, Long> {
    Category findByName(String name);
}
