package com.inn.coffee.dao;

import com.inn.coffee.POJO.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ShopDao extends JpaRepository<Shop, Integer> {
    Shop findByAddress(@Param("address") String address);
}
