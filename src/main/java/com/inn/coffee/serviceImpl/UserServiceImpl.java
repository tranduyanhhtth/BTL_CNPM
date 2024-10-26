package com.inn.coffee.serviceImpl;

import com.inn.coffee.POJO.User;
import com.inn.coffee.contents.CoffeeConstants;
import com.inn.coffee.dao.UserDao;
import com.inn.coffee.service.UserService;
import com.inn.coffee.utils.CoffeeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return CoffeeUtils.getResponseEntity("Successfully Registered.", HttpStatus.OK);
                } else {
                    return CoffeeUtils.getResponseEntity("Email already exits.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CoffeeUtils.getResponseEntity(CoffeeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CoffeeUtils.getResponseEntity(CoffeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        } else {
            return false;
        }
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestMap.get("email"),
                            requestMap.get("password")
                    )
            );

            if (authentication.isAuthenticated()) {
                // Assuming we fetch the user status from the authenticated principal
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (user != null && "true".equals(user.getStatus())) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return CoffeeUtils.getResponseEntity("Login successful.", HttpStatus.OK);
                } else {
                    return CoffeeUtils.getResponseEntity("Account is not activated.", HttpStatus.UNAUTHORIZED);
                }
            }
        } catch (Exception ex) {
            log.error("Login error: {}", ex.getMessage());
        }
        return CoffeeUtils.getResponseEntity("Invalid credentials.", HttpStatus.UNAUTHORIZED);

    }
}
