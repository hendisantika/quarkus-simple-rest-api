package com.hendisantika.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * Project : horizon1-product
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 14/10/21
 * Time: 12.22
 */

@Entity
@Table(name = "product")
@NamedQuery(name = "Product.findAll",
        query = "SELECT p FROM Product p ORDER BY p.name")
public class Product {
    @Id
    @SequenceGenerator(
            name = "productSequence",
            sequenceName = "product_id_seq",
            allocationSize = 1,
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
    public Integer id;

    @Column(length = 150, unique = true)
    public String name;

    @Column(length = 200, unique = true)
    public String description;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
