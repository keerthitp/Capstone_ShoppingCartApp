package com.company.ShoppingCartServer.Service;

import com.company.ShoppingCartServer.DAO.ProductRepository;
import com.company.ShoppingCartServer.DTO.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    @Autowired
    ProductRepository productRepoMock;

    @InjectMocks
    ProductService productService;

    Product product1, product2, product3;

    List<Product> productList;

    @Before
    public void setup() throws Exception {



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


    }

    @Test
    public void shouldGetAllProducts(){

        productList = Arrays.asList(product1,product2);

        when(productRepoMock.findAll()).thenReturn(productList);

        assertEquals(2, productService.getAllProductsFromDatabase().size());

        assertThat(productService.getAllProductsFromDatabase(), containsInAnyOrder(product1, product2));

    }

    @Test
    public void shouldAddProduct(){

        // add a product
        when(productRepoMock.save(product1)).thenReturn(product1);

        assertEquals(product1, productService.addProductToDatabase(product1));

    }

    @Test
    public void shouldDeleteProduct() throws Exception{

        when(productRepoMock.save(product1)).thenReturn(product1);

        productService.addProductToDatabase(product1);

        productService.deleteProductFromDatabase(product1.getId());
        verify(productRepoMock).deleteById(product1.getId());

    }

    @Test
    public void shouldUpdateProduct(){


        when(productRepoMock.findById(product1.getId())).thenReturn(Optional.of(product1));
        when(productRepoMock.save(product1)).thenReturn(product1);

        productService.addProductToDatabase(product1);

        //change product price from 12.49 to 21.99
        product1.setPrice(21.99);

        assertEquals(product1, productService.updateProductInDatabase(product1));
    }

    @Test
    public void shouldUpdateProductThroughPatch(){

        Map<String, Object> productMap = new HashMap<>();
        productMap.put("price", 25.2);



        when(productRepoMock.findById(product1.getId())).thenReturn(Optional.of(product1));
        when(productRepoMock.save(product1)).thenReturn(product1);
     //   productService.updatePartialProductInDatabase(productMap,product1.getId());

        //change product price from 12.49 to 21.99


        assertEquals(product1.getPrice(), productService.updateProductInDatabase(product1).getPrice());
        verify(productRepoMock).findById(product1.getId());
    }

}
