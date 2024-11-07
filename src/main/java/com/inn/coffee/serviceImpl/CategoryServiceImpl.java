package com.inn.coffee.serviceImpl;

import com.google.common.base.Strings;
import com.inn.coffee.JWT.JwtFilter;
import com.inn.coffee.POJO.Category;
import com.inn.coffee.contents.CoffeeConstants;
import com.inn.coffee.dao.CategoryDao;
import com.inn.coffee.service.CategoryService;
import com.inn.coffee.utils.CoffeeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateCategoryMap(requestMap, false)){
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return CoffeeUtils.getResponseEntity("Category Added Successfully", HttpStatus.OK);
                }
            }else{
                return CoffeeUtils.getResponseEntity(CoffeeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            log.error("Exception occurred while adding category", ex);
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd){
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("Inside if");
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(), HttpStatus.OK);
        }catch (Exception ex){
            log.error("Exception occurred while getting all category", ex);
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()) {
                if(validateCategoryMap(requestMap, true)){
                    Optional<Category> optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(optional.isPresent()){
                        categoryDao.save(getCategoryFromMap(requestMap, true));
                        return CoffeeUtils.getResponseEntity("Category Updated Successfully", HttpStatus.OK);
                    }else{
                        return CoffeeUtils.getResponseEntity("Category id doesn't not exist", HttpStatus.OK);
                    }
                }
                return CoffeeUtils.getResponseEntity(CoffeeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            else{
                return CoffeeUtils.getResponseEntity(CoffeeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            log.error("Exception occurred while updating category", ex);
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
