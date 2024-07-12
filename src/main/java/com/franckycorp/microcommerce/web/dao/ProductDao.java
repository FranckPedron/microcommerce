package com.franckycorp.microcommerce.web.dao;

import com.franckycorp.microcommerce.web.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    Product findById(int id);

    List<Product> findByPrixGreaterThan(int prixLimit);

    List<Product> findAllByOrderByNomAsc();

//    @Query("select id, nom, prix from Product p where p.prix > :prixLimit")
//    List<Product> chercherUnProduitCher(@Param("prixLimit") int prix);


}
