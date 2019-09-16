package com.company.ShoppingCartServer.Controller;

import com.company.ShoppingCartServer.DTO.Product;
import com.company.ShoppingCartServer.DTO.Transaction;
import com.company.ShoppingCartServer.Service.PurchaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PurchaseControllerTest {

    private MockMvc mockMvc;

    @Mock
    PurchaseService purchaseServiceMock;



    @InjectMocks
    PurchaseController purchaseController;

    Product product1, product2, product3;

    List<Product> productList;

    @Before
    public void setup(){

        product1 = new Product();
        product1.setId(1);
        product1.setName("Inferno");
        product1.setCategory("Book");
        product1.setQuantity(10);
        product1.setDomesticOrImported("Domestic");
        product1.setPrice(12.49);

        product1.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        product2 = new Product();
        product2.setId(2);
        product2.setName("Hip hop music CD");
        product2.setCategory("Music");
        product2.setQuantity(8);
        product2.setDomesticOrImported("Domestic");
        product2.setPrice(14.99);

        product2.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        product3 = new Product();
        product3.setId(3);
        product3.setName("Chocolate Bar");
        product3.setCategory("Food");
        product3.setQuantity(20);
        product3.setDomesticOrImported("Domestic");
        product3.setPrice(0.85);

        product3.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

    }

    @Test
    public void shouldGenerateInvoice() throws Exception{

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(purchaseController).build();

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
        product1WithTax.setCategory("Book");
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

        System.out.println(transaction);
        when(purchaseServiceMock.createReceiptForTheTransaction(productList))
                .thenReturn(transaction);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String productListString = ow.writeValueAsString(productList);

        MvcResult transactionResult = mockMvc.perform(post("/purchase")

                .contentType(MediaType.APPLICATION_JSON_UTF8)

                .content(productListString)
        )
                .andDo(print())
                .andExpect(status().isOk())
//                                          .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.total").value(total))
                .andReturn();



        verify(purchaseServiceMock).createReceiptForTheTransaction(productList);

        

    }
}
