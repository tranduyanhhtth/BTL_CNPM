package com.inn.coffee.wrapper;

import lombok.Data;

@Data
public class BillWrapper {
    Integer id;

    String name;

    String uuid;

    Integer total;

    String paymentMethod;

    public BillWrapper(){}

    public BillWrapper(Integer id, String name, String uuid, Integer total, String paymentMethod) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
        this.total = total;
        this.paymentMethod = paymentMethod;
    }
}

