package com.inn.coffee.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShopWrapper {

    private String id;

    private String name;

    private String address;

    private String contactNumber;

    private String status;

    public ShopWrapper(String id, String name, String address, String contactNumber, String status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.status = status;
    }
}
