package com.company.ShoppingCartServer.Temp;

import com.company.ShoppingCartServer.Temp.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="product") // Table called product will be created
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    @NotEmpty
    private String productName;

    @NotNull
    private Integer productPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pcId") // , updatable = false, insertable = false)

    //@JoinColumn(name="pcId")
    private ProductCategory productCategory;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
}
