package com.franckycorp.microcommerce.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.franckycorp.microcommerce.web.dao.ProductDao;
import com.franckycorp.microcommerce.web.exceptions.ProduitIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import com.franckycorp.microcommerce.web.model.Product;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @GetMapping("/Produits")
    public MappingJacksonValue listeProduits() {
        Iterable<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listeDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique",
                monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listeDeNosFiltres);
        return produitsFiltres;
    }

    @GetMapping("/Produits/{id}")
    public Product afficherUnProduit(@PathVariable("id") int id) {
        Product produit = productDao.findById(id);
        if (produit == null) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        return produit;
    }

    @GetMapping("/test/produits/{prixLimit}")
    public List<Product> testDeRequetes(@PathVariable("prixLimit") int prixLimit) {
        return productDao.findByPrixGreaterThan(prixLimit);
    }

    @PostMapping("/Produits")
    public ResponseEntity<Product> ajouterProduit(@RequestBody Product product) {
        Product productAdded = productDao.save(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/Produits/{id}")
    public void supprimerProduit(@PathVariable("id") int id) {
        productDao.deleteById(id);
    }

    @PutMapping("/Produits")
    public void updateProduit(@RequestBody Product product) {
        productDao.save(product);
    }
}

