package com.inn.coffee.dao;

import com.inn.coffee.POJO.User;
import com.inn.coffee.wrapper.ShopWrapper;
import com.inn.coffee.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser();

    List<String> getAllAdmin();

    @Transactional
    @Modifying
    void updateStatus(@Param("status") String status, @Param("id") Integer id);

    User findByEmail(String email);
}
