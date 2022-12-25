package com.beprime.service.services.interfaceService;

import com.beprime.persistance.entities.Group;

import java.util.List;

public interface IGroupService extends IGenericService<Group, Long> {
    Group save(Group groupDto);

    Group update(Group group);

    Group findById(Long id);

    void addRoleToUse(String userName, String roleName);

    List<Group> findAllEGroupe();

    void delete(Long id);

    Group findByPrivileged(String nom);
}
