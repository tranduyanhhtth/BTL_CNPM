package com.inn.coffee.restImpl;

import com.inn.coffee.contents.CoffeeConstants;
import com.inn.coffee.rest.UserRest;
import com.inn.coffee.service.UserService;
import com.inn.coffee.utils.CoffeeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try{
            return userService.signUp(requestMap);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{
            return userService.login(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
