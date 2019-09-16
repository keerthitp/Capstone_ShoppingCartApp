package com.company.ShoppingCartServer.Controller;

import com.company.ShoppingCartServer.DTO.Product;
import com.company.ShoppingCartServer.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductControllerTests {

    private MockMvc mockMvc;

    @Mock
    ProductService productServiceMock;

    @InjectMocks
    ProductController productController;

    Product product1, product2, product3;

    List<Product> productList;

    @Before
    public void setup() throws Exception {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        product1 = new Product();
        product1.setId(1);
        product1.setName("Inferno");
        product1.setCategory("Book");
        product1.setQuantity(10);
        product1.setDomesticOrImported("Domestic");
        product1.setPrice(12.49);
        product1.setQuantityToBuy(0);
        product1.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        product2 = new Product();
        product2.setId(2);
        product2.setName("Hip hop music CD");
        product2.setCategory("Music");
        product2.setQuantity(8);
        product2.setDomesticOrImported("Domestic");
        product2.setPrice(14.99);
        product2.setQuantityToBuy(0);
        product2.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        product3 = new Product();
        product3.setId(3);
        product3.setName("Chocolate Bar");
        product3.setCategory("Food");
        product3.setQuantity(20);
        product3.setDomesticOrImported("Domestic");
        product3.setPrice(0.85);
        product3.setQuantityToBuy(0);
        product3.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        productList = Arrays.asList(product1,product2);
    }

    @Test
    public void rootContext_ShouldRespondWith404() throws Exception{

        mockMvc.perform(get("/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetAllProducts() throws Exception{

        when(productServiceMock.getAllProductsFromDatabase())
                .thenReturn(productList);

        mockMvc.perform(get("/product"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name" , is(productList.get(0).getName())));
    }

    @Test
    public void shouldAddAProduct() throws Exception{

        Product product3;

        product3 = new Product();
        product3.setName("Chocolate Bar");
        product3.setCategory("Food");
        product3.setQuantity(20);
        product3.setDomesticOrImported("Domestic");
        product3.setPrice(0.85);
        product3.setQuantityToBuy(0);
        product3.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        product3.setId(3);

        when(productServiceMock.addProductToDatabase(Mockito.any(Product.class))).thenReturn(product3);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String product3ObjStr = ow.writeValueAsString(product3);

        MvcResult addProductResult = mockMvc.perform(post("/product")

                                                        .contentType(MediaType.APPLICATION_JSON_UTF8)

                                                    .content(product3ObjStr)
                                                    )
                                            .andDo(print())
                                            .andExpect(status().isOk())
//                                          .andExpect(jsonPath("$", hasSize(1)))
                                         .andExpect(jsonPath("$.price",is(product3.getPrice())))
                                            .andReturn();


       verify(productServiceMock).addProductToDatabase(product3);

    }

    @Test
    public void shouldDeleteProduct() throws  Exception{

        MvcResult deleteProductResult = mockMvc.perform(delete("/product/"+ product2.getId().toString()))
                                                .andExpect(status().isOk())
                                                .andReturn();

        verify(productServiceMock).deleteProductFromDatabase(product2.getId());

    }

    @Test
    public void shouldUpdateProduct() throws Exception{
        // Changing the price of a product from 14.99 to 13.99 in product 2

        product2.setPrice(13.99);

        //when(productServiceMock.updateProductInDatabase(product2)).thenReturn(product2);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String product2ObjStr = ow.writeValueAsString(product2);

        MvcResult product2UpdateResult = mockMvc.perform(put("/product")
                                                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                                .content(product2ObjStr))
                                                .andExpect(status().isOk())
                                                .andReturn();

       verify(productServiceMock).updateProductInDatabase(product2);
    }

    @Test
    public void shouldUpdateProductWithPatch() throws Exception{
        // Changing the price of a product from 14.99 to 13.99 in product 2



        Map<String, Object> productMap = new HashMap<>();
        productMap.put("price", 25.2);


        when(productServiceMock.updatePartialProductInDatabase(productMap,product2.getId()))
                .thenReturn(new ResponseEntity("resource updated", HttpStatus.OK));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String product2ObjStr = ow.writeValueAsString(productMap);

        MvcResult product2UpdateResult = mockMvc.perform(patch("/product/"+ product2.getId().toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(product2ObjStr))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("resource updated"))

                .andReturn();

        verify(productServiceMock).updatePartialProductInDatabase(productMap,product2.getId());
    }

    @Test
    public void shouldThrowExceptionOnPatchForManipulatingProductId() throws Exception{



        // Trying to change the id of a product

        Map<String, Object> productMap = new HashMap<>();
        productMap.put("id", 9091);


        when(productServiceMock.updatePartialProductInDatabase(productMap,product2.getId()))
                .thenReturn(new ResponseEntity("ID cannot be updated by User", HttpStatus.UNPROCESSABLE_ENTITY));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String product2ObjStr = ow.writeValueAsString(productMap);

        MvcResult product2UpdateResult = mockMvc.perform(patch("/product/"+ product2.getId().toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(product2ObjStr))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(content().string("ID cannot be updated by User"))

                .andReturn();

        verify(productServiceMock).updatePartialProductInDatabase(productMap,product2.getId());
    }

}
