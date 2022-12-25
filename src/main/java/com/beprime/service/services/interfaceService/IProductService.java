package com.beprime.service.services.interfaceService;

import com.beprime.persistance.entities.Product;

import java.util.List;

public interface IProductService extends IGenericService<Product, Long> {
    Product save(Product productDto) throws Exception;

    Product update(Product productDto) throws Exception;

    Product findById(Long id);

    List<Product> findAllSProducts();

    void delete(Long id);

    List<Product> findAllSProductsByCategoy(Long id);

    List<Product> findAllSProductsByActive(boolean active);

    List<Product> findAllSProductsByState(boolean state);
}
