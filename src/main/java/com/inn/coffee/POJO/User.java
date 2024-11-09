package com.inn.coffee.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")

@NamedQuery(name = "User.getAllUser", query = "select new com.inn.coffee.wrapper.UserWrapper(u.id,u.name,u.email,u.contactNumber,u.status) from User u where u.role='user'")

@NamedQuery(name = "User.updateStatus", query = "update User u set u.status=:status where u.id=:id")

@NamedQuery(name = "User.getAllAdmin", query = "select u.email from User u where u.role='admin'")

@NamedQuery(name = "User.getAllShop", query = "select new com.inn.coffee.wrapper.ShopWrapper(s.id,s.name,s.address,s.contactNumber,s.status) from Shop s")

@Data                   // Lombok annotation to create all the getters, setters, equals, hash, and toString methods based on fields
@Entity                 // Specifies that the class is an entity and is mapped to a database table
@DynamicUpdate          // Hibernate annotations that optimize SQL updates
@DynamicInsert          // Hibernate annotations that optimize SQL inserts
@Table(name = "user")   // Specifies the table name in the database
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Make sure that the object before and after serialization is one
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;
}
