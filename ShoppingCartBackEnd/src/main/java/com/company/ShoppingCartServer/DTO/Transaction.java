package com.company.ShoppingCartServer.DTO;

import java.math.BigDecimal;
import java.util.List;

public class Transaction {


    private List<Product> productList;
    private BigDecimal salesTax;
    private BigDecimal Total;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public BigDecimal getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax) {
        this.salesTax = salesTax;
    }

    public BigDecimal getTotal() {
        return Total;
    }

    public void setTotal(BigDecimal total) {
        Total = total;
    }
}

