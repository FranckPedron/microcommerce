package com.franckycorp.microcommerce.web.controller;

import com.franckycorp.microcommerce.web.dao.ProductDao;
import org.springframework.web.bind.annotation.*;

import com.franckycorp.microcommerce.web.model.Product;

import java.util.List;

@RestController
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao){
        this.productDao = productDao;
    }

    @GetMapping("/Produits")
    public List<Product> listeProduits() {
        return productDao.findAll();
    }

    @GetMapping("/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        return productDao.findById(id);
    }

    @PostMapping("/Produits")
    public void ajouterProduit(@RequestBody Product product) {
        productDao.save(product);
    }
}
