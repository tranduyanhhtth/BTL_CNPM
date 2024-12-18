package com.inn.coffee.service;

import com.inn.coffee.wrapper.ShopWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ShopService {

    ResponseEntity<List<ShopWrapper>> getAllShop();

    ResponseEntity<String> addNewShop(Map<String, String> requestMap);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<Map<String, Object>> getShopBills(Integer id);

    ResponseEntity<String> deleteShop(Integer id);

    ResponseEntity<String> updateShop(Integer id, Map<String, String> requestMap);
}
