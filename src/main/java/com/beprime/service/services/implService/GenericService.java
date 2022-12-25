package com.beprime.service.services.implService;


import com.beprime.persistance.dao.BaseRepository;
import com.beprime.service.services.interfaceService.IGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Slf4j
public class GenericService<T, D extends Serializable> implements IGenericService<T, D> {

    private static final int INDEX_BEGIN = 31;
    public static final int SQL_SERVER_IN_CLAUSE_PARTITION_SIZE = 2000;
    private static final String IS_DELETED_FIELD = "isDeleted";

    @Autowired
    private BaseRepository<T, D> baseRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public GenericService() {
        super();
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return baseRepository.saveAndFlush(entity);
    }

    @Override
    public T findOne(D id) {
        return baseRepository.findOne(id);
    }

    @Override
    public void delete(D id) {
        UUID uuid = UUID.randomUUID();
        baseRepository.delete(id, uuid);
    }

    @Override
    public void delete(T entity) {
        UUID uuid = UUID.randomUUID();
        baseRepository.delete(entity, uuid);
    }

    @Override
    public void deleteList(Iterable<T> entities) {
        baseRepository.deleteAll(entities);
    }

    @Override
    public boolean existById(D id) {
        return baseRepository.findOne(id) != null;
    }

}
