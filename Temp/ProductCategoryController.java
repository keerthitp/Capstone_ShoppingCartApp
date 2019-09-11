package com.company.ShoppingCartServer.Temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/productCategory")
public class ProductCategoryController {

    @Autowired
    ProductCategoryRepository productCategoryRepo;

    @GetMapping
    public List<ProductCategory> getAllProductCategory(){
        return productCategoryRepo.findAll();
    }

    @PostMapping
    public ProductCategory addProductCategory(@RequestBody ProductCategory productCategory){

        return productCategoryRepo.save(productCategory);
    }

    @DeleteMapping(value = "/{productCategoryId}")
    public void deleteProductCategory(@Valid @PathVariable Integer productCategoryId){
        productCategoryRepo.deleteById(productCategoryId);
    }


}
