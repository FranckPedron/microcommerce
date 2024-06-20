package com.franckycorp.microcommerce.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.franckycorp.microcommerce.model.Product;

@RestController
public class ProductController {

    @GetMapping("/Produits")
    public String listeProduits() {
        return "Un exemple de produit";
    }

    @GetMapping("/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        return new Product(id, "Aspirateur", 100);
    }
}
