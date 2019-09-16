package com.company.ShoppingCartServer.Service;

import com.company.ShoppingCartServer.DTO.Product;
import com.company.ShoppingCartServer.DTO.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseService {

//    @Autowired
//    ProductService productService;

    public Transaction createReceiptForTheTransaction(List<Product> productList){

        Transaction transaction = new Transaction();

        List<Product> transactionProductList = new ArrayList<>();
        List<BigDecimal> salesTaxByProductList = new ArrayList<>();
        List<String> exemptProductCategory = new ArrayList<>();

        BigDecimal salesTax,importedDutyTax;
        BigDecimal finalSalesTax, finalTotal;

        int counter =1;

        DecimalFormat twoDForm = new DecimalFormat("0.00");


        exemptProductCategory.add("Books");
        exemptProductCategory.add("Food");
        exemptProductCategory.add("Medical");



        for(Product product: productList){
            //if(exemptProductCategory.contains(product.getCategory()))
            if(exemptProductCategory.stream().anyMatch(product.getCategory()::equalsIgnoreCase))
                salesTax = BigDecimal.ZERO;
            else
                salesTax = BigDecimal.valueOf(0.10);

            if(product.getDomesticOrImported().equalsIgnoreCase("imported"))
                importedDutyTax = BigDecimal.valueOf(0.05);
            else
                importedDutyTax = BigDecimal.ZERO;

            Product productToAdd = new Product();
            Double SalesTaxForProduct;


            BigDecimal totalTaxForTheCurrentProduct;

            BigDecimal tempSalesTax =  BigDecimal.valueOf(product.getPrice())
                    .multiply(salesTax)
                    .multiply(BigDecimal.valueOf(product.getQuantityToBuy()));
            //System.out.println("SalesTaxFor the product: " + tempSalesTax);

            BigDecimal tempImportedDutyTax = BigDecimal.valueOf(product.getPrice() )
                    .multiply(BigDecimal.valueOf(product.getQuantityToBuy()))
                    .multiply(importedDutyTax);
           // System.out.println("imported tax for the product: " + tempImportedDutyTax);

            BigDecimal bigDecimal20 = BigDecimal.valueOf(20);

            totalTaxForTheCurrentProduct =  BigDecimal.valueOf(Math.ceil(Double.parseDouble((tempSalesTax.add(tempImportedDutyTax))
                                            .multiply(bigDecimal20).toString()))).divide(bigDecimal20);

/*
                    BigDecimal.valueOf(Math.ceil(Double.parseDouble(
                    (tempImportedDutyTax.add(tempSalesTax))
                            .multiply(bigDecimal20)
                            .divide(bigDecimal20).toString())));
*/

//            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//            System.out.println("totalTaxForTheCurrentProduct: " + totalTaxForTheCurrentProduct);
//            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");



            salesTaxByProductList.add((totalTaxForTheCurrentProduct.setScale(2,BigDecimal.ROUND_UP)));
      //      System.out.println("Tax: "+ (totalTaxForTheCurrentProduct).setScale(2,BigDecimal.ROUND_UP));

            productToAdd.setId(counter++);
            productToAdd.setName(product.getName());
            productToAdd.setPrice(Double.parseDouble(
                    (BigDecimal.valueOf(product.getQuantityToBuy())
                    .multiply(BigDecimal.valueOf(product.getPrice())))
                    .add(totalTaxForTheCurrentProduct).setScale(2,BigDecimal.ROUND_UP).toString()));
            productToAdd.setQuantityToBuy(product.getQuantityToBuy());
//            productToAdd.setDomesticOrImported(product.getDomesticOrImported());
//            productToAdd.setQuantity(product.getQuantity());

            transactionProductList.add(productToAdd);









        }

        transaction.setProductList(transactionProductList);

        finalSalesTax =BigDecimal.ZERO;
        finalTotal= BigDecimal.ZERO;

        for (int i =0; i< transaction.getProductList().size(); i++){
            finalSalesTax = finalSalesTax.add((salesTaxByProductList.get(i)));
            //+= salesTaxByProductList.get(i);
            finalTotal = finalTotal.add(BigDecimal.valueOf( transaction.getProductList().get(i).getPrice()));
        }

        transaction.setSalesTax(finalSalesTax);
        transaction.setTotal(finalTotal);


//        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//        System.out.println("totalTaxForTheCurrentProduct: " + finalSalesTax);
//        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");


        //        transaction.setSalesTax(new Double(finalSalesTax));
//        transaction.setTotal(new Double(finalTotal));

        return transaction;

    }
}
