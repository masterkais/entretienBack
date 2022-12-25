package com.beprime.persistance.dao;

import com.beprime.persistance.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends BaseRepository<User, Long> {
    User findByUserName(String firstName);

    User findByUserNameAndPassword(String login, String password);

    @Query("select u from User u where u.isDeleted =true ")
    List<User> findAllUserIsDeleted();

    @Query(value = "select * from t_user_groups where groups_gr_id = :id", nativeQuery = true)
    List<Long> getAllUsersIdByIdGroups(@Param("id") Long id);

    @Query(value = "select delivery_man_list_u_id from t_request_transfert_delivery_man_list where request_transfert_rt_id = :id", nativeQuery = true)
    List<Long> getAllDriverMansIdByIdRequest(@Param("id") Long id);
}
