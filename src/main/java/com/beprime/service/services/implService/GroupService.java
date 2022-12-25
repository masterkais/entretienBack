package com.beprime.service.services.implService;

import com.beprime.persistance.dao.GroupDao;
import com.beprime.persistance.dao.UserDao;
import com.beprime.persistance.entities.Group;
import com.beprime.persistance.entities.User;
import com.beprime.persistance.errors.ApiErrors;
import com.beprime.service.services.interfaceService.IGroupService;
import com.beprime.service.services.utils.errors.ErrorsResponse;
import com.beprime.service.services.utils.http.HttpCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GroupService extends GenericService<Group, Long> implements IGroupService {
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private UserDao userDao;

    @Override
    public void addRoleToUse(String userName, String roleName) {
        Group group = groupDao.findByPrivileged(roleName);
        User user = userDao.findByUserName(userName);
        user.getGroups().add(group);
        userDao.save(user);
    }

    @Override
    public Group save(Group groupDto) {
        Objects.requireNonNull(groupDto);
        if (groupDao.findByPrivileged(groupDto.getPrivileged()) != null) {
            throw new HttpCustomException(ApiErrors.OBJECT_EXISTS, new ErrorsResponse().error(groupDto.getPrivileged()));
        }
        Group groupeSaved = groupDao.saveAndFlush(groupDto);
        return groupDto;
    }

    @Override
    public Group update(Group groupDto) {
        Objects.requireNonNull(groupDto);
        if (groupDao.findByPrivileged(groupDto.getPrivileged()) != null) {
            throw new HttpCustomException(ApiErrors.OBJECT_EXISTS, new ErrorsResponse().error(groupDto.getPrivileged()));
        }
        Group group=groupDao.findOne(groupDto.getId());
        group.setPrivileged(groupDto.getPrivileged());
        Group groupeSaved = groupDao.saveAndFlush(group);
        return groupeSaved;
    }

    @Override
    public Group findById(Long id) {
        return Optional.ofNullable(groupDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id)));

    }

    @Override
    public List<Group> findAllEGroupe() {
        List<Group> groups = groupDao.findAll();
        return groups;
    }

    @Override
    public Group findByPrivileged(String nom) {
        return Optional.ofNullable(groupDao.findByPrivileged(nom)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(nom)));

    }

    public List<Group> findAllGroupByUserId(Long idUser) {
        List<Long> groupIds = this.groupDao.getAllGroupsIdByIdUser(idUser);
        List<Group> groupDtos = new ArrayList<>();
        groupIds.forEach((idGroup) -> {
            Group group = this.groupDao.findOne(idGroup);
            groupDtos.add(group);
        });
        return groupDtos;
    }
}
