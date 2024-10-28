package com.inn.coffee.serviceImpl;

import com.inn.coffee.JWT.JwtFilter;
import com.inn.coffee.POJO.Category;
import com.inn.coffee.POJO.Product;
import com.inn.coffee.contents.CoffeeConstants;
import com.inn.coffee.dao.ProductDao;
import com.inn.coffee.service.ProductService;
import com.inn.coffee.utils.CoffeeUtils;
import com.inn.coffee.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isUser()){
                if(validateProductMap(requestMap, false)){
                    productDao.save(getProductFromMap(requestMap, false));
                    return CoffeeUtils.getResponseEntity("Product Added Successfully", HttpStatus.OK);
                }
                return CoffeeUtils.getResponseEntity(CoffeeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else{
                return CoffeeUtils.getResponseEntity(CoffeeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        }else {
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try{
            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateProductMap(requestMap, true)){
                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(optional.get().getStatus());
                        productDao.save(product);
                        return CoffeeUtils.getResponseEntity("Product Updated Successfully", HttpStatus.OK);
                    }else{
                        return CoffeeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
                    }
                }else{
                    return CoffeeUtils.getResponseEntity(CoffeeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            }else{
                return CoffeeUtils.getResponseEntity(CoffeeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            if(jwtFilter.isAdmin()){
                Optional optional = productDao.findById(id);
                if(!optional.isEmpty()){
                    productDao.deleteById(id);
                    return CoffeeUtils.getResponseEntity("Product Deleted Successfully", HttpStatus.OK);
                }
                return CoffeeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
            }else{
                return CoffeeUtils.getResponseEntity(CoffeeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                Optional optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()){
                    productDao.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return CoffeeUtils.getResponseEntity("Product Status Updated Successfully", HttpStatus.OK);
                }
                return CoffeeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
            }else{
                return CoffeeUtils.getResponseEntity(CoffeeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try{
            return new ResponseEntity<>(productDao.getProductByCategory(id), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try{
            return new ResponseEntity<>(productDao.getProductById(id), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
