package com.company.ShoppingCartServer.Service;

import com.company.ShoppingCartServer.DTO.Product;
import com.company.ShoppingCartServer.DTO.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {

    Product product1, product2, product3;

    List<Product> productList;

    @InjectMocks
    PurchaseService purchaseService;

    @Before
    public void setup(){

        product1 = new Product();
        product1.setId(1);
        product1.setName("Inferno");
        product1.setCategory("Books");
        product1.setQuantity(10);
        product1.setDomesticOrImported("Domestic");
        product1.setQuantityToBuy(1);
        product1.setPrice(12.49);

        product1.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        product2 = new Product();
        product2.setId(2);
        product2.setName("Hip hop music CD");
        product2.setCategory("Music");
        product2.setQuantity(8);
        product2.setDomesticOrImported("Domestic");
        product2.setQuantityToBuy(1);
        product2.setPrice(14.99);

        product2.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        product3 = new Product();
        product3.setId(3);
        product3.setName("Chocolate Bar");
        product3.setCategory("Food");
        product3.setQuantity(20);
        product3.setDomesticOrImported("Domestic");
        product3.setQuantityToBuy(1);
        product3.setPrice(0.85);

        product3.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

    }

    @Test
    public void shouldGenerateInvoice() {



        // productList contains the list of products in the cart
        productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        // this transaction contains the the (product list price* quantity) + tax
        Transaction transaction = new Transaction();

        Product product1WithTax, product2WithTax, product3WithTax;

        // total is the grand total of all the products + all taxes
        BigDecimal salesTax, total; // salesTax is the cummulative tax of all the products + import duty tax

        List<Product> returnProductList = new ArrayList<>();

        product1WithTax = new Product();

        product1WithTax = new Product();
        product1WithTax.setId(1);
        product1WithTax.setName("Inferno");
        product1WithTax.setCategory("Books");
        product1WithTax.setQuantity(10);
        product1WithTax.setDomesticOrImported("Domestic");
        product1WithTax.setPrice(12.49);
        product1WithTax.setQuantityToBuy(1);
        product1WithTax.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        product2WithTax = new Product();

        product2WithTax = new Product();
        product2WithTax.setId(2);
        product2WithTax.setName("Hip hop music CD");
        product2WithTax.setCategory("Music");
        product2WithTax.setQuantity(8);
        product2WithTax.setDomesticOrImported("Domestic");
        product2WithTax.setPrice(16.49);
        product2WithTax.setQuantityToBuy(1);
        product2WithTax.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        product3WithTax = new Product();

        product3WithTax = new Product();
        product3WithTax.setId(3);
        product3WithTax.setName("Chocolate Bar");
        product3WithTax.setCategory("Food");
        product3WithTax.setQuantity(20);
        product3WithTax.setDomesticOrImported("Domestic");
        product3WithTax.setPrice(0.85);
        product3WithTax.setQuantityToBuy(1);
        product3WithTax.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        salesTax = BigDecimal.valueOf(1.5);
        total = BigDecimal.valueOf(29.83);

        returnProductList.add(product1WithTax);
        returnProductList.add(product2WithTax);
        returnProductList.add(product3WithTax);

        transaction.setProductList(returnProductList);
        transaction.setSalesTax(salesTax);
        transaction.setTotal(total);


        assertThat(transaction.getSalesTax(), Matchers.comparesEqualTo(purchaseService.createReceiptForTheTransaction(productList).getSalesTax()));




    }
}
