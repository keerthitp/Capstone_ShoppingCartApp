package com.company.ShoppingCartServer.Controller;

import com.company.ShoppingCartServer.DTO.Product;
import com.company.ShoppingCartServer.DTO.Transaction;
import com.company.ShoppingCartServer.Service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5000")

@RestController
@RequestMapping(value="/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping
    public Transaction createReceipt(@RequestBody List<Product> productList){
        System.out.println("******************* Request received for generating invoice*****************");
        return purchaseService.createReceiptForTheTransaction(productList);
    }
}
