package com.franckycorp.microcommerce.web.dao;

import com.franckycorp.microcommerce.model.Product;
//import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();
    Product findById(int id);
    Product save(Product product);
}
