package com.beprime.service.restController;

import com.beprime.persistance.entities.Category;
import com.beprime.service.services.interfaceService.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/category")
public class CategoryController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/categorys")
    public List<Category> findAll() {
        return categoryService.findAllCategorie();
    }

    @GetMapping(value = "/categorysDispo")
    public List<Category> findAllCategorysDispo() {
        return categoryService.findAllCategorieDispo();
    }

    @GetMapping(value = "/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping()
    public Category save(@RequestBody @Valid Category categoryDto) {
        return categoryService.save(categoryDto);
    }

    @PutMapping()
    public Category updae(@RequestBody @Valid Category categoryDto) {
        return categoryService.update(categoryDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        categoryService.delete(id);
        return true;
    }


}
