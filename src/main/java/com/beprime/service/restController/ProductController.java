package com.beprime.service.restController;

import com.beprime.persistance.entities.Product;
import com.beprime.service.services.implService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public List<Product> findAll() {
        return productService.findAllSProducts();
    }

    @GetMapping(value = "/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping()
    public Product save(@RequestBody @Valid Product productDto) throws Exception {
        return productService.save(productDto);
    }


    @PutMapping()
    public Product update(@RequestBody @Valid Product productDto) throws Exception {

        return productService.update(productDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        productService.delete(id);
        return true;
    }

    @GetMapping(value = "/products/categoryId/{id}")
    public List<Product> findAllByCategoryId(@PathVariable Long id) {
        return productService.findAllSProductsByCategoy(id);
    }

    @GetMapping(value = "/products/active/{active}")
    public List<Product> findAllByActive(@PathVariable boolean active) {
        return productService.findAllSProductsByActive(active);
    }

    @GetMapping(value = "/products/state/{state}")
    public List<Product> findAllByState(@PathVariable boolean state) {
        return productService.findAllSProductsByState(state);
    }

}
