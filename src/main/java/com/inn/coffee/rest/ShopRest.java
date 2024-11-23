package com.inn.coffee.rest;

import com.inn.coffee.wrapper.BillWrapper;
import com.inn.coffee.wrapper.ShopWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/shop")
public interface ShopRest {
    @GetMapping(path = "/getShop")
    ResponseEntity<List<ShopWrapper>> getAllShop();

    @PostMapping(path = "/addShop")
    ResponseEntity<String> addNewShop(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/getShopBills")
    ResponseEntity<List<BillWrapper>> getShopBills();
}
