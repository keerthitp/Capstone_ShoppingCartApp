package com.company.ShoppingCartServer.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="product") // Table called product will be created
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;

    @NotNull
    private Double price;

    @NotEmpty
    private String category;

    @NotEmpty
    private String domesticOrImported;

    @NotNull
    private Integer quantity;

    private Integer quantityToBuy = 0; // this will be zero for the product added to the Inventory

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getQuantityToBuy() {
        return quantityToBuy;
    }

    public void setQuantityToBuy(Integer quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDomesticOrImported() {
        return domesticOrImported;
    }

    public void setDomesticOrImported(String domesticOrImported) {
        this.domesticOrImported = domesticOrImported;
    }
}
