package com.company.ShoppingCartServer.DTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(productList, that.productList) &&
                Objects.equals(salesTax, that.salesTax) &&
                Objects.equals(Total, that.Total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productList, salesTax, Total);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "productList=" + productList +
                ", salesTax=" + salesTax +
                ", Total=" + Total +
                '}';
    }
}

