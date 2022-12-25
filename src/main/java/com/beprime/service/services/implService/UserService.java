package com.beprime.service.services.implService;

import com.beprime.persistance.constants.Constants;
import com.beprime.persistance.dao.GroupDao;
import com.beprime.persistance.dao.UserDao;
import com.beprime.persistance.entities.Group;
import com.beprime.persistance.entities.User;
import com.beprime.persistance.errors.ApiErrors;
import com.beprime.service.services.interfaceService.IUserService;
import com.beprime.service.services.utils.errors.ErrorsResponse;
import com.beprime.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService extends GenericService<User, Long> implements IUserService {
    private UserDao userDao;
    private GroupDao groupDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService(UserDao userDao, GroupDao groupDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findUserByName(String userName) {
        User user = userDao.findByUserName(userName);
        List<Group> groups = new ArrayList<>();
        List<Long> groupsIds = this.groupDao.getAllGroupsIdByIdUser(user.getId());
        groupsIds.forEach((id) -> {
            groups.add(this.groupDao.findOne(id));
        });
        user.setGroups(groups);
        return userDao.findByUserName(userName);
    }

    @Override
    public User save(User userDto) {
        Objects.requireNonNull(userDto);
        userDto.setActive(false);
        if (userDao.findByUserName(userDto.getUserName()) != null) {
            throw new RuntimeException("this user aleready exist");
        }
        String hachPW = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(hachPW);

        User savedUser = userDao.saveAndFlush(userDto);
        return userDto;


    }

    @Override
    public User update(User userDto) {
        Objects.requireNonNull(userDto);
        User user = userDao.findOne(userDto.getId());
        user.setGroups(userDto.getGroups());
        user.setDateCreated(userDto.getDateCreated());
        user.setActive(userDto.isActive());
        user.setDateNaissanced((userDto.getDateNaissanced()));
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAdress(userDto.getAdress());
        user.setCity(userDto.getCity());
        User savedUser = userDao.saveAndFlush(user);
        log.info(Constants.LOG_ENTITY_UPDATED, savedUser);
        return savedUser;
    }

    @Override
    public User findById(Long id) {
        return Optional.ofNullable(userDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id)));
    }

    @Override
    public List<User> findAllUsers() {
        List<User> usertList = userDao.findAll();
        List<User> userDtos = new ArrayList<>();
        usertList.forEach(user -> {
            userDtos.add(user);
        });
        return userDtos;
    }

    @Override
    public List<User> findAllDriversByRequest(Long id) {
        List<Long> userIdList = userDao.getAllDriverMansIdByIdRequest(id);
        List<User> usertList = new ArrayList<>();
        userIdList.forEach((data) -> {
            usertList.add(this.userDao.findOne(data));
        });
        List<User> userDtos = new ArrayList<>();
        usertList.forEach(user -> {
            userDtos.add(user);
        });
        return userDtos;
    }

    @Override
    public void addPrevilegeToUser(String userName, String privileged) {
        User user = userDao.findByUserName(userName);
        Group group = groupDao.findByPrivileged(privileged);
        user.getGroups().add(group);
        userDao.saveAndFlush(user);
    }

    @Override
    public List<User> findUsersByPrivilege(String privilege) {
        Group group = groupDao.findByPrivileged(privilege);
        System.out.println(group.getPrivileged());
        List<Long> usersIds = new ArrayList<>();
        usersIds = userDao.getAllUsersIdByIdGroups(group.getId());
        System.out.println(usersIds.size());
        List<User> userDtoList = new ArrayList<>();
        usersIds.forEach((usersId) -> {
            User user = userDao.findOne(usersId);
            if (user != null && user.isDeleted() == false) {
                userDtoList.add(user);
            }
        });
        return userDtoList;
    }

    @Override
    public void delete(Long id) {
        userDao.deleteById(id);
    }

}
