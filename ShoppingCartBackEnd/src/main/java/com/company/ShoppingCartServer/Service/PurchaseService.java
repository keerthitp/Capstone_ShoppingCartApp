package com.company.ShoppingCartServer.Service;

import com.company.ShoppingCartServer.DTO.Product;
import com.company.ShoppingCartServer.DTO.Transaction;
import org.springframework.stereotype.Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PurchaseService {

    public Transaction createReceiptForTheTransaction(List<Product> productList){

        Transaction transaction = new Transaction();

        List<Product> transactionProductList = new ArrayList<>();
        List<Double> salesTaxByProductList = new ArrayList<>();
        List<String> exemptProductCategory = new ArrayList<>();

        double salesTax,importedDutyTax;
        double finalSalesTax, finalTotal;

        int counter =1;

        DecimalFormat twoDForm = new DecimalFormat("0.00");

        exemptProductCategory.add("Books");
        exemptProductCategory.add("Food");
        exemptProductCategory.add("Medical");



        for(Product product: productList){
            if(exemptProductCategory.contains(product.getCategory()))
                salesTax =0;
            else
                salesTax = 0.10;

            if(product.getDomesticOrImported().equalsIgnoreCase("imported"))
                importedDutyTax = 0.05;
            else
                importedDutyTax = 0;

            Product productToAdd = new Product();
            Double SalesTaxForProduct;


            double totalTaxForTheCurrentProduct;
            double tempSalesTax =  product.getPrice() * product.getQuantity() * salesTax;
            double tempImportedDutyTax = product.getPrice() * product.getQuantity() * importedDutyTax;

            totalTaxForTheCurrentProduct = Math.ceil((tempImportedDutyTax + tempSalesTax)*20.0)/20.0;

            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            System.out.println("totalTaxForTheCurrentProduct: " + totalTaxForTheCurrentProduct);
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");



            salesTaxByProductList.add(new Double(Double.valueOf(twoDForm.format(totalTaxForTheCurrentProduct))));
            System.out.println("Tax: "+ Double.valueOf(twoDForm.format(totalTaxForTheCurrentProduct)));

            productToAdd.setId(counter++);
            productToAdd.setName(product.getName());
            productToAdd.setPrice(
                    Double.valueOf(twoDForm.format(totalTaxForTheCurrentProduct
                            + (product.getQuantity() * product.getPrice())

                    )));
            System.out.println("Price: "+ Double.valueOf(twoDForm.format(totalTaxForTheCurrentProduct
                                            + (product.getQuantity() * product.getPrice())

                                    )));

            transactionProductList.add(productToAdd);









        }

        transaction.setProductList(transactionProductList);

        finalSalesTax =finalTotal= 0.0;

        for (int i =0; i< transaction.getProductList().size(); i++){
            finalSalesTax += salesTaxByProductList.get(i);
            finalTotal += transaction.getProductList().get(i).getPrice();
        }

        transaction.setSalesTax(Double.valueOf(twoDForm.format(finalSalesTax)));
        transaction.setTotal(Double.valueOf(twoDForm.format(finalTotal)));


        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println("totalTaxForTheCurrentProduct: " + twoDForm.format(Double.valueOf(finalSalesTax)));
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");


        //        transaction.setSalesTax(new Double(finalSalesTax));
//        transaction.setTotal(new Double(finalTotal));

        return transaction;

    }
}
