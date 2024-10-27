package com.inn.coffee.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

    UserWrapper user = new UserWrapper("5657","abc@gmail.com",1,"abc","false");

    private Integer id;

    private String name;

    private String email;

    private String contactNumber;

    private String status;

    public UserWrapper(String contactNumber, String email, Integer id, String name, String status) {
        this.contactNumber = contactNumber;
        this.email = email;
        this.id = id;
        this.name = name;
        this.status = status;
    }
}
