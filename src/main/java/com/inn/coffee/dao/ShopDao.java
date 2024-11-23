package com.inn.coffee.dao;

import com.inn.coffee.POJO.Bill;
import com.inn.coffee.POJO.Shop;
import com.inn.coffee.wrapper.BillWrapper;
import com.inn.coffee.wrapper.ProductWrapper;
import com.inn.coffee.wrapper.ShopWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopDao extends JpaRepository<Shop, Integer> {

    List<ShopWrapper> getAllShop();

    List<BillWrapper> getShopBills();

    @Transactional
    @Modifying
    void updateShopStatus(@Param("status") String status, @Param("id") Integer id);

}
