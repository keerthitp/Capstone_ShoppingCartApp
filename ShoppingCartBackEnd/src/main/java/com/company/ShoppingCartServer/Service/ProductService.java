package com.company.ShoppingCartServer.Service;

import com.company.ShoppingCartServer.DAO.ProductRepository;
import com.company.ShoppingCartServer.DTO.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Component
public class ProductService {

    /*
    Autowiring feature of spring framework enables you to inject the object dependency implicitly.
    It internally uses setter or constructor injection.
     */

    @Autowired
    ProductRepository productRepo;


    public Product addProductToDatabase(Product product) {

        return productRepo.save(product);

    }

    public List<Product> getAllProductsFromDatabase() {
        return productRepo.findAll();
    }


    public void deleteProductFromDatabase(Integer id) {

        try {
            productRepo.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Product id " + id + " does not exist in the database");
        }
    }

    public Product updateProductInDatabase(Product product) {

//        if(id != product.getId())
//            throw new IllegalArgumentException("Id "+ id+ " does not match with Product Id: "+ product.getId());

        Product productToBeUpdated = getProductByIdFromDatabase(product.getId());


        return productRepo.save(product);
    }

    public ResponseEntity<?> updatePartialProductInDatabase(Map<String, Object> partialProduct, Integer productId) {

        Product product = getProductByIdFromDatabase(productId);

        partialProduct.forEach((k, v) -> {

            switch (k) {
                case "id":
                    throw new IllegalArgumentException("ID cannot be updated by User - it is system generated");

                case "name":
                    product.setName(String.valueOf(v));
                    break;
                case "price":
                    product.setPrice(Double.parseDouble(v.toString()));

                    break;
                case "category":
                    product.setCategory(v.toString());
                    break;
                case "domesticOrImported":
                    product.setDomesticOrImported((v.toString()));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field");
            }

        });

        productRepo.save(product);

        //productRepo.save(partialProduct,productId);

        return ResponseEntity.ok("resource updated");


    }

    public Product getProductByIdFromDatabase(Integer id) {

        Product product = productRepo.findById(id).orElse(null);

        if (product == null)
            throw new IllegalArgumentException("Invalid id passed");
        else
            return product;


    }

    public void updateInventory(List<Product> cartProducts) {

        List<Product> productList = productRepo.findAll();

        for (Product cartProduct : cartProducts) {

            Product product = productRepo.getOne(cartProduct.getId());

            if (product.getQuantity() > cartProduct.getQuantityToBuy())
                product.setQuantity(product.getQuantity() - cartProduct.getQuantityToBuy());


        }


    }
}