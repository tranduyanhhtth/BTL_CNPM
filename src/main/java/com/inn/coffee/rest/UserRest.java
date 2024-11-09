package com.inn.coffee.rest;

import com.inn.coffee.wrapper.ShopWrapper;
import com.inn.coffee.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/getUser")
    public ResponseEntity<List<UserWrapper>> getAllUser();

    @GetMapping(path = "/getShop")
    public ResponseEntity<List<ShopWrapper>> getAllShop();

    @PostMapping(path = "/update")
    public ResponseEntity<String> updateUser(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/checkToken")
    public ResponseEntity<String> checkToken();

    @PostMapping(path = "/changePassword")
    ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/forgotPassword")
    ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/addShop")
    ResponseEntity<String> addShop(@RequestBody Map<String, String> requestMap);
}
