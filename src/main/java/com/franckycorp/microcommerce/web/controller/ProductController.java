package com.franckycorp.microcommerce.web.controller;

import com.franckycorp.microcommerce.web.dao.ProductDao;
import com.franckycorp.microcommerce.web.exceptions.ProduitGratuitException;
import com.franckycorp.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.franckycorp.microcommerce.web.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Produits", description = "API pour les opérations CRUD sur les produits.")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Operation(summary = "Récupère la liste des produits.")
    @GetMapping("/Produits")
    public List<Product> listeProduits() {
        return productDao.findAll();
    }

    @Operation(summary = "Retourne la liste des produits par ordre alphabétique.")
    @GetMapping("/TriProduits")
    public List<Product> trierProduitsParOrdreAlphabetique() {
        return productDao.findAllByOrderByNomAsc();
    }

    @Operation(summary = "Récupère un produit grâce à son ID .")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produit trouvé."),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé.")
    })
    @GetMapping("/Produits/{id}")
    public Product afficherUnProduit(@Parameter(name = "id", description = "id du produit", example = "5", required = true) @PathVariable("id") int id) {
        Product produit = productDao.findById(id);
        if (produit == null)
            throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        return produit;
    }

    @Operation(summary = "Récupère la liste des produits dont le prix est supérieur à une valeur donnée.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produits trouvés."),
            @ApiResponse(responseCode = "404", description = "Produits non trouvés.")
    })
    @GetMapping("/test/produits/{prixLimit}")
    public List<Product> testDeRequetes(@Parameter(name = "prixLimit", description = "prix minimmum", example = "195", required = true) @PathVariable("prixLimit") int prixLimit) {
        List<Product> produits = productDao.findByPrixGreaterThan(prixLimit);
        if (produits.isEmpty())
            throw new ProduitIntrouvableException("Aucun produit trouvé avec un prix supérieur à " + prixLimit);
        return produits;
    }

    @Operation(summary = "Ajoute un produit.")
    @PostMapping("/Produits")
    public ResponseEntity<Product> ajouterProduit(@Valid @RequestBody Product product) {
        if (product.getPrix() == 0) {
            throw new ProduitGratuitException("Le produit ne peut pas être gratuit.");
        }
        Product productAdded = productDao.save(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Supprime un produit.")
    @DeleteMapping("/Produits/{id}")
    public void supprimerProduit(@PathVariable("id") int id) {
        productDao.deleteById(id);
    }

    @Operation(summary = "Met à jour un produit.")
    @PutMapping("/Produits")
    public void updateProduit(@RequestBody Product product) {
        productDao.save(product);
    }

    @Operation(summary = "Calcule la marge d'un produit.")
    @GetMapping("/AdminProduits")
    public List<String> afficherMargeProduit() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(product -> product.toString() + " : " + calculerMargeProduit(product))
                .collect(Collectors.toList());
    }

    public Integer calculerMargeProduit(Product product) {
        return product.getPrix() - product.getPrixAchat();
    }

}

