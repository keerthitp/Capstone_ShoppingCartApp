import { Injectable } from '@angular/core';
import { Product } from './product';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {Invoice} from './invoice';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  shoppingCartProducts:Product[]=[];
  productToAddToTheShoppingCart:Product;

  serverURL:String = "http://localhost:8081"; // 

  constructor(private http:HttpClient) { 

  }

  addProductToShoppingCart(product:Product){

    let found =0;
    //check if this product is already in the cart
    this.shoppingCartProducts.map(
      productInCart=> {
        
       if( productInCart.id==product.id){
         console.log("productInCart: "+ productInCart.name+"-"+ productInCart.id);
         console.log("product: "+ product.name+"-"+product.id);
          found =1;
          productInCart.quantityToBuy+=product.quantityToBuy;
          return;
       }
      }
    )
    if (found == 0)
      this.shoppingCartProducts.push(product);
      /* comment */


  }

  getAllShoppingCartProducts(){
    return this.shoppingCartProducts;
  }

  getReceipt(): Observable<Invoice>{
    console.log("Listing the shopping cart before calling the server");
    console.log(this.shoppingCartProducts);

    console.log("Making an api call to generate the invoice");
    
    const url = `${this.serverURL}/purchase`;
    return this.http.post<Invoice>(url,this.shoppingCartProducts);

  }

  clearShoppingCart(){
    this.shoppingCartProducts=[];
  }
  
}
