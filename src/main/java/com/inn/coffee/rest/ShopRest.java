package com.inn.coffee.rest;

import com.inn.coffee.wrapper.BillWrapper;
import com.inn.coffee.wrapper.ShopWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/getShopBills/{id}")
    ResponseEntity<Map<String, Object>> getShopBills(@PathVariable Integer id);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteShop(@PathVariable Integer id);
}
