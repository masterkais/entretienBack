package com.beprime.service.services.interfaceService;

import com.beprime.persistance.entities.User;

import java.util.List;

public interface IUserService extends IGenericService<User, Long> {
    User save(User User);

    User update(User user);

    User findById(Long id);

    List<User> findAllUsers();

    void delete(Long id);

    User findUserByName(String userName);

    List<User> findAllDriversByRequest(Long id);

    void addPrevilegeToUser(String userName, String privileged);

    List<User> findUsersByPrivilege(String privilege);
}
