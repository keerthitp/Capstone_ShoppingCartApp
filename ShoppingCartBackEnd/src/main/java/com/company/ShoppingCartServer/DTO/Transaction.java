package com.company.ShoppingCartServer.DTO;

import java.util.List;

public class Transaction {


    private List<Product> productList;
    private Double salesTax;
    private Double Total;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(Double salesTax) {
        this.salesTax = salesTax;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }
}
