package com.company.ShoppingCartServer.DAO;



import com.company.ShoppingCartServer.DTO.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
