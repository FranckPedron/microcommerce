package com.franckycorp.microcommerce.web.dao;

import com.franckycorp.microcommerce.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao{
    public static List<Product> products = List.of(
            new Product(1, "Ordinateur portable", 350),
            new Product(2, "Aspirateur Robot", 500),
            new Product(3, "Table de Ping Pong", 750)
    );

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(int id) {
        for (Product product : products){
            if (product.getId() == id){
                return product;
            }
        }
        return null;
    }

    @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }
}
