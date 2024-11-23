package com.inn.coffee.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

@NamedQuery(name = "Shop.getAllShop", query = "select new com.inn.coffee.wrapper.ShopWrapper(s.id,s.name,s.address,s.contactNumber,s.status) from Shop s")

@NamedQuery(name = "Shop.updateShopStatus", query = "update Shop s set s.status=:status where s.id=:id")

@NamedQuery(name = "Shop.getShopBills", query = "select new com.inn.coffee.wrapper.BillWrapper(b.id,b.name,b.uuid,b.total,b.paymentMethod) from Bill b, Shop s where LEFT(b.uuid, 2) = s.id")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "shop")
public class Shop implements Serializable {

    public static final Long serialVersionUid = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
