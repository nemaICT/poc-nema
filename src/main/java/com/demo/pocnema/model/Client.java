package com.demo.pocnema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/*Data is a Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.*/
@Data
/* Entity is a JPA annotation to make this object ready for storage in a JPA-based data store*/
@Entity
@Table(name = "clients") // create table clients in the apiclinet database
@EntityListeners(AuditingEntityListener.class)

//Spring Boot uses Jackson for Serializing and Deserializing Java objects to and from JSON.
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)

public class Client implements Serializable{

    private @Id
    //@GeneratedValue annotation is used to define the primary key generation strategy
    // declared the primary key to be an Auto Increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // @NotBlank annotation is used to validate that the annotated field is not null or empty.
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;


    // @Column annotation is used to define the properties of the column that will be mapped to the annotated field
    @Column(nullable = false, updatable = false)
    // @Temporal annotation is used with java.util.Date and java.util.Calendar classes.
    // It converts the date and time values from Java Object to compatible database type and vice versa.
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;


    public Client(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Client() {
    }
}
