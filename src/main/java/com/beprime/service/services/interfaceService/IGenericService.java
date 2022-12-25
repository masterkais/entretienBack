package com.beprime.service.services.interfaceService;

import java.io.Serializable;
import java.util.List;

public interface IGenericService<T, D extends Serializable> {

    List<T> findAll();
    T findOne(D id);
    <S extends T> S saveAndFlush(S entity);
    void delete(D id);
    void delete(T entity);
    void deleteList(Iterable<T> entities);
    boolean existById(D id);



}
