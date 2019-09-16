import { Injectable } from '@angular/core';
import { Product } from './product';
import { Observable, BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {Invoice} from './invoice';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  shoppingCartProducts:Product[]=[];
  productToAddToTheShoppingCart:Product;

  serverURL:String = "http://localhost:8080"; // 

  // The below code is used to share total number of cart items with navbar - so that it can display the number of items in cart
  private messageSource = new BehaviorSubject(0);
  currentMessage = this.messageSource.asObservable();

  
  changeMessage(message: number) {
    this.messageSource.next(message)
  }


  
   calculateCartItems(){
    let total = 0
    
    if(this.getAllShoppingCartProducts().length == 0)
      this.changeMessage(0);

    for(let i =0; i< this.getAllShoppingCartProducts().length; i++){
        total+= this.shoppingCartProducts[i].quantityToBuy;
    console.log("Total: "+ total);
    this.changeMessage(total);
    }
  }
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
          
          if ((productInCart.quantityToBuy + product.quantityToBuy)<= product.quantity){
          productInCart.quantityToBuy+=product.quantityToBuy;
          this.calculateCartItems();
          return;
          }
       }
      }
    )
    if (found == 0 && product.quantityToBuy <= product.quantity){
      this.shoppingCartProducts.push(product);
    this.calculateCartItems();
    }
      /* comment */


  }

  removeProductFromCart(index:number){
      this.shoppingCartProducts.splice(index,1);
      this.calculateCartItems();
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
    this.calculateCartItems();
  }
 
  decreaseQuantityOnTheProductInShoppingCart(index:number){



    if(this.shoppingCartProducts[index].quantityToBuy>0)
       this.shoppingCartProducts[index].quantityToBuy--;

       if(this.shoppingCartProducts[index].quantityToBuy == 0)
        this.removeProductFromCart(index);
      
        this.getAllShoppingCartProducts();
      // this.calculateTotal();
        this.calculateCartItems();
  }

  increaseQuantityOnTheProductInShoppingCart(index: number){
    if(this.shoppingCartProducts[index].quantityToBuy>0 && this.shoppingCartProducts[index].quantityToBuy <= this.shoppingCartProducts[index].quantity)
       this.shoppingCartProducts[index].quantityToBuy++;

       

       this.getAllShoppingCartProducts();
   // this.calculateTotal();
      this.calculateCartItems();
  }
 
  
}
