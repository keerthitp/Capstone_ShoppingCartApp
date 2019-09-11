package com.company.ShoppingCartServer.Controller;

import com.company.ShoppingCartServer.DTO.Product;
import com.company.ShoppingCartServer.DTO.Transaction;
import com.company.ShoppingCartServer.Service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping
    public Transaction createReceipt(@RequestBody List<Product> productList){

        return purchaseService.createReceiptForTheTransaction(productList);
    }
}
