package com.company.ShoppingCartServer.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="product") // Table called product will be created
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name cannot be empty, add atleast a length of 3 characters")
    @Length(min = 3, max = 255)
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
        if(imageUrl.length()==0){
            this.imageUrl="https://images.pexels.com/photos/1342251/pexels-photo-1342251.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";

        } else
            this.imageUrl = imageUrl;
    }

    public Integer getQuantityToBuy() {
        return this.quantityToBuy;
    }

    public void setQuantityToBuy(Integer quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        if(!Arrays.asList("food", "books", "medical", "music", "luxury items", "clothes").contains(category.toLowerCase()))
            throw new IllegalArgumentException("Expected category: "+  "food"+ " books" + " medical" +  " music " + " luxury items " + " clothes");

        this.category = category;
    }

    public String getDomesticOrImported() {
        return this.domesticOrImported;
    }

    public void setDomesticOrImported(String domesticOrImported) {
        this.domesticOrImported = domesticOrImported;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", domesticOrImported='" + domesticOrImported + '\'' +
                ", quantity=" + quantity +
                ", quantityToBuy=" + quantityToBuy +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) &&
                name.equals(product.name) &&
                price.equals(product.price) &&
                category.equals(product.category) &&
                domesticOrImported.equals(product.domesticOrImported) &&
                quantity.equals(product.quantity) &&
                quantityToBuy.equals(product.quantityToBuy) &&
                imageUrl.equals(product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category, domesticOrImported, quantity, quantityToBuy, imageUrl);
    }
}
