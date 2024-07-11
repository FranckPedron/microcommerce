package com.franckycorp.microcommerce.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

//@JsonFilter("monFiltreDynamique")
@Entity
@Getter
@Setter
public class Product {


    @Id
    @GeneratedValue
    private int id;

    @Size(min = 3, max = 25)
    private String nom;

    @Min(value = 1)
    private int prix;

    @Schema(description = "Le prix d'achat du produit.")
    private int prixAchat;

    public Product() {
    }

    public Product(int id, String nom, int prix, int prixAchat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
    }

    @Override
    public String toString() {
        return "product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                '}';
    }
}
