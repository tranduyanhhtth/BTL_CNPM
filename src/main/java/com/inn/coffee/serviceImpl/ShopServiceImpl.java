package com.inn.coffee.serviceImpl;

import com.inn.coffee.JWT.JwtFilter;
import com.inn.coffee.POJO.*;
import com.inn.coffee.contents.CoffeeConstants;
import com.inn.coffee.dao.ShopDao;
import com.inn.coffee.service.ShopService;
import com.inn.coffee.utils.CoffeeUtils;
import com.inn.coffee.wrapper.BillWrapper;
import com.inn.coffee.wrapper.ShopWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ShopServiceImpl implements ShopService {

    private static final Logger log = LoggerFactory.getLogger(ShopServiceImpl.class);
    @Autowired
    ShopDao shopDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<List<ShopWrapper>> getAllShop() {
        try{
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(shopDao.getAllShop(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex){
            log.error("Exception occurred while fetching all users", ex);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addNewShop(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateShopMap(requestMap, false)){
                    shopDao.save(getShopFromMap(requestMap, false));
                    return CoffeeUtils.getResponseEntity("Shop Added Successfully", HttpStatus.OK);
                }
                return CoffeeUtils.getResponseEntity(CoffeeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else{
                return CoffeeUtils.getResponseEntity(CoffeeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            log.error("Exception occurred while adding shop", ex);
        }

        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                Optional<Shop> optional = shopDao.findById(Integer.parseInt(requestMap.get("id")));
                if(optional.isPresent()){
                    shopDao.updateShopStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return CoffeeUtils.getResponseEntity("Shop Status Updated Successfully", HttpStatus.OK);
                }
                return CoffeeUtils.getResponseEntity("Shop id does not exist", HttpStatus.OK);
            }else{
                return CoffeeUtils.getResponseEntity(CoffeeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            log.error("Exception occurred while updating shop status", ex);
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<Map<String, Object>> getShopBills(Integer id) {
        try{
            if(jwtFilter.isAdmin()){
                List<BillWrapper> bills = shopDao.getShopBills(id);
                Integer totalAmount = shopDao.getTotalAmount(id);

                Map<String, Object> result = new HashMap<>();
                result.put("bills", bills);
                result.put("totalAmount", totalAmount);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }else{
                Map<String, Object> unauthorizedResponse = new HashMap<>();
                unauthorizedResponse.put("message", "Unauthorized access");

                return new ResponseEntity<>(unauthorizedResponse, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            log.error("Exception occurred while counting bills", ex);
        }
        Map<String, Object> error = new HashMap<>();
        error.put("message", "Unauthorized access");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateShopMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Shop getShopFromMap(Map<String, String> requestMap, boolean isAdd) {
        Shop shop = new Shop();
        if(isAdd){
            shop.setId(Integer.parseInt(requestMap.get("id")));
        }else {
            shop.setStatus("true");
        }
        shop.setName(requestMap.get("name"));
        shop.setAddress(requestMap.get("address"));
        shop.setContactNumber(requestMap.get("contactNumber"));
        shop.setStatus(requestMap.get("status"));
        return shop;
    }

    @Override
    public ResponseEntity<String> deleteShop(Integer id) {
        try{
            if(jwtFilter.isAdmin()){
                Optional<Shop> optional = shopDao.findById(id);
                if(optional.isPresent()){
                    shopDao.deleteShop(id);
                    shopDao.deleteById(id);
                    return CoffeeUtils.getResponseEntity("Shop Deleted Successfully", HttpStatus.OK);
                }
                return CoffeeUtils.getResponseEntity("Shop id does not exist", HttpStatus.OK);
            }else{
                return CoffeeUtils.getResponseEntity(CoffeeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            log.error("Exception occurred while deleting shop", ex);
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
