package com.company.ShoppingCartServer.Controller;

import com.company.ShoppingCartServer.DAO.ProductRepository;
import com.company.ShoppingCartServer.DTO.Product;
import com.company.ShoppingCartServer.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5000")
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> getAllProducts(){

         return productService.getAllProductsFromDatabase();
    }

    @GetMapping(value = "/{id}")
    public Product getProductById(@PathVariable Integer id){

        return productService.getProductByIdFromDatabase(id);
    }

    @PostMapping
    public Product addProduct(@Valid @RequestBody Product product){

        return productService.addProductToDatabase(product);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable Integer id){

        productService.deleteProductFromDatabase(id);
    }

    @PutMapping
    public Product updateProductInDatabase(@Valid @RequestBody Product product){
        return  productService.updateProductInDatabase(product);
    }

    @PatchMapping(value="/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePartialProductInDatabase(@RequestBody Map<String,Object> partialProduct, @PathVariable Integer id){
        return  productService.updatePartialProductInDatabase(partialProduct, id);
    }
}
