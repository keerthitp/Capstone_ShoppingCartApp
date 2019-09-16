package com.company.ShoppingCartServer.DAO;

import com.company.ShoppingCartServer.DTO.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepo;

    Product product1, product2, product3;

    List<Product> productList;

    @Before
    public void setup() throws Exception {

        productRepo.deleteAll();

        product1 = new Product();

        product1.setName("Inferno");
        product1.setCategory("Book");
        product1.setQuantity(10);
        product1.setDomesticOrImported("Domestic");
        product1.setPrice(12.49);
        product1.setQuantityToBuy(0);
        product1.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        product2 = new Product();

        product2.setName("Hip hop music CD");
        product2.setCategory("Music");
        product2.setQuantity(8);
        product2.setDomesticOrImported("Domestic");
        product2.setPrice(14.99);
        product2.setQuantityToBuy(0);
        product2.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        product3 = new Product();

        product3.setName("Chocolate Bar");
        product3.setCategory("Food");
        product3.setQuantity(20);
        product3.setDomesticOrImported("Domestic");
        product3.setPrice(0.85);
        product3.setQuantityToBuy(0);
        product3.setImageUrl("https://images.pexels.com/photos/2067488/pexels-photo-2067488.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");


    }

    @Test
    @Transactional
    public void shoullGetAllProducts(){

        productRepo.save(product1);
        productRepo.save(product2);

        productList = new ArrayList<>();
        productList.add(product2);
        productList.add(product1);

        List<Product> productListFromRepo = productRepo.findAll();

        assertThat(productListFromRepo, containsInAnyOrder(product1, product2));
    }

    @Test
    @Transactional
    public void shouldGetProductById(){

        // add 2 product through ProductRepository
        // and check whether if a product can be found through its id

        productRepo.save(product1);
        productRepo.save(product2);

        assertEquals(product1, productRepo.getOne(product1.getId()));
    }

    @Test
    @Transactional
    public void shouldAddProduct(){

        // when a product is added, it does not have id so check id is non zero after adding

        productRepo.save(product1);

        assertTrue(product1.getId()>0);
        assertNotNull(product1.getId());

    }

    @Test
    @Transactional
    public void shouldUpdateProduct(){

        productRepo.save(product1);
        // product price - $12.49
        //change it to $15.66

        product1.setPrice(15.66);
        productRepo.save(product1);

        Product productFromRepo = productRepo.getOne(product1.getId());
        assertEquals(new Double(15.66), productFromRepo.getPrice());

    }

    @Test
    @Transactional
    public void shouldDeleteProduct(){

        productRepo.save(product1);
        productRepo.save(product2);
        //Delete the product

        productRepo.deleteById(product1.getId());

        // check whether that product is still there

        assertEquals(1, productRepo.findAll().size());
    }





}
