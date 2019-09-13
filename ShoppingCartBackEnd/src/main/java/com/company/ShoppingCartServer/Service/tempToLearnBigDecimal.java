package com.company.ShoppingCartServer.Service;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public class tempToLearnBigDecimal {
    public static void main(String[] args) {

        Double p1 = 27.99;
        Double p2 = 18.99;
        Double p3 = 9.75;
        Double p4 = 11.25;

        BigDecimal price1 = BigDecimal.valueOf(p1);
        BigDecimal price2 = BigDecimal.valueOf(p2);
        BigDecimal price3 = BigDecimal.valueOf(p3);
        BigDecimal price4 = BigDecimal.valueOf(p4);

        BigDecimal bD20 = new BigDecimal("20");
        BigDecimal salesTax = new BigDecimal("0.10");
        BigDecimal importedTax = new BigDecimal("0.05");

        BigDecimal price1AfterTax,price2AfterTax, price3AfterTax ,price4AfterTax,temp,
        tax1, tax2, tax3, tax4;

        temp = (price1.multiply(salesTax)).add(price1.multiply(importedTax));
        System.out.println("P1 price"+ price1);
        System.out.println("P1 tax"+ temp.toString());
        tax1 = temp;
        tax1 =
                                    new BigDecimal(Math.ceil(Double.parseDouble((temp.multiply(bD20).toString() ))))
                                    .divide(bD20);



        System.out.println("P1 tax after rounding to 5c"+ tax1.toString());

        price1AfterTax = price1.add(tax1);

        System.out.println("P1 price after adding tax"+ price1AfterTax.toString());

        temp = (price2.multiply(salesTax)).add(price2.multiply(BigDecimal.ZERO));
        tax2 = temp;
        price2AfterTax =
                new BigDecimal(Math.ceil(Double.parseDouble((temp.multiply(bD20).toString() ))))
                        .divide(bD20);

        tax2 = price2AfterTax;
        price2AfterTax = price2.add(price2AfterTax);


        temp = (price3.multiply(BigDecimal.ZERO)).add(price3.multiply(BigDecimal.ZERO));
        tax3 = temp;
        tax3 =
                new BigDecimal(Math.ceil(Double.parseDouble((temp.multiply(bD20).toString() ))))
                        .divide(bD20);

        price3AfterTax = price3.add(tax3);




        temp = (price4.multiply(BigDecimal.ZERO)).add(price4.multiply(importedTax));
        tax4 = temp;
        tax4 =
                new BigDecimal(Math.ceil(Double.parseDouble((temp.multiply(bD20).toString() ))))
                        .divide(bD20);


        price4AfterTax = tax4.add(price4);

        BigDecimal totalTax = tax1.add(tax2).add(tax3).add(tax4);
        BigDecimal consolidatedTotal = price1AfterTax.add(price2AfterTax).add(price3AfterTax).add(price4AfterTax);

        System.out.println("P1: "+ price1AfterTax);
        System.out.println("P2: "+ price2AfterTax);
        System.out.println("P3: "+ price3AfterTax);
        System.out.println("P4: "+ price4AfterTax);

        System.out.println("Total tax: "+ totalTax.setScale(2,BigDecimal.ROUND_UP));
        System.out.println("Total: "+ consolidatedTotal);


    }
}
