package com.beprime.persistance.dao;

import com.beprime.persistance.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends BaseRepository<Product, Long> {
    @Query(value = "select * from t_product where ca_id= :ca_id and pr_is_deleted= FALSE", nativeQuery = true)
    List<Product> getAllProductByCategoryId(@Param("ca_id") Long ca_id);

    @Query(value = "select * from t_product where pr_active= :pr_active and pr_is_deleted= FALSE", nativeQuery = true)
    List<Product> getAllProductByActive(@Param("pr_active") boolean pr_active);

    @Query(value = "select * from t_product where pr_state= :pr_state and pr_is_deleted= FALSE", nativeQuery = true)
    List<Product> getAllProductByState(@Param("pr_state") boolean pr_state);

}

