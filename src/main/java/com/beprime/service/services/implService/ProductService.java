package com.beprime.service.services.implService;

import com.beprime.persistance.dao.CategoryDao;
import com.beprime.persistance.dao.ProductDao;
import com.beprime.persistance.entities.Product;
import com.beprime.persistance.errors.ApiErrors;
import com.beprime.service.services.interfaceService.IProductService;
import com.beprime.service.services.utils.errors.ErrorsResponse;
import com.beprime.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.beprime.persistance.constants.Constants.LOG_ENTITY_CREATED;
import static com.beprime.persistance.constants.Constants.LOG_ENTITY_UPDATED;

@Service
@Slf4j
public class ProductService extends GenericService<Product, Long> implements IProductService {

    private final ProductDao productDao;
    private final CategoryDao categoryDao;

    @Autowired
    private ProductService(ProductDao productDao, CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @Override
    public Product save(Product productDto) throws Exception {
        Objects.requireNonNull(productDto);
        Product productSaved = productDao.saveAndFlush(productDto);
        log.info(LOG_ENTITY_CREATED, productSaved);
        return productSaved;
    }


    @Override
    public Product update(Product productDto) throws Exception {
        Objects.requireNonNull(productDto);
        Product product1 = productDao.findOne(productDto.getId());
        product1.setActive(productDto.isActive());
        product1.setBuyingPrice(productDto.getBuyingPrice());
        product1.setSellingPrice(productDto.getSellingPrice());
        product1.setDescription(productDto.getDescription());
        product1.setCategory(productDto.getCategory());
        product1.setName(productDto.getName());
        product1.setState(productDto.isState());
        Product productSaved = productDao.saveAndFlush(product1);
        log.info(LOG_ENTITY_UPDATED, productSaved);
        return productSaved;
    }

    @Override
    public Product findById(Long id) {
        return Optional.ofNullable(productDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id)));
    }

    @Override
    public List<Product> findAllSProducts() {
        List<Product> productList = productDao.findAll();
        return productList;
    }

    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        if (findById(id) != null) {
            productDao.delete(id, uuid);
        }
    }

    @Override
    public List<Product> findAllSProductsByCategoy(Long id) {
        List<Product> productList = productDao.getAllProductByCategoryId(id);
        return productList;
    }

    @Override
    public List<Product> findAllSProductsByActive(boolean active) {
        List<Product> productList = productDao.getAllProductByActive(active);
        return productList;
    }

    @Override
    public List<Product> findAllSProductsByState(boolean state) {
        List<Product> productList = productDao.getAllProductByState(state);
        return productList;
    }

}
