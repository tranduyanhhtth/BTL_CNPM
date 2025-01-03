package com.inn.coffee.restImpl;

import com.inn.coffee.contents.CoffeeConstants;
import com.inn.coffee.rest.ShopRest;
import com.inn.coffee.service.ShopService;
import com.inn.coffee.utils.CoffeeUtils;
import com.inn.coffee.wrapper.ShopWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ShopRestImpl implements ShopRest{
    //private static final Logger log = LoggerFactory.getLogger(ShopRestImpl.class);
    @Autowired
    ShopService shopService;

    @Override
    public ResponseEntity<List<ShopWrapper>> getAllShop() {
        try {
            return shopService.getAllShop();
        } catch (Exception ex) {
            log.error("Exception in getAllShop", ex);
        }
        return new ResponseEntity<List<ShopWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addNewShop(Map<String, String> requestMap) {
        try {
            return shopService.addNewShop(requestMap);
        } catch (Exception ex) {
            log.error("Exception in addShop", ex);
        }
        return new ResponseEntity<String>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            return shopService.updateStatus(requestMap);
        }catch (Exception ex){
            log.error("Exception in updateShop", ex);
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getShopBills(Integer id) {
        try{
            return shopService.getShopBills(id);
        }catch (Exception ex){
            log.error("Exception in countBills", ex);
        }
        Map<String, Object> error = new HashMap<>();
        error.put("message", "Unauthorized access");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteShop(Integer id) {
        try{
            return shopService.deleteShop(id);
        }catch (Exception ex){
            log.error("Exception in deleteShop", ex);
        }

        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateShop(Integer id, Map<String, String> requestMap) {
        try{
            return shopService.updateShop(id, requestMap);
        }catch (Exception ex){
            log.error("Exception in updateShop", ex);
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
