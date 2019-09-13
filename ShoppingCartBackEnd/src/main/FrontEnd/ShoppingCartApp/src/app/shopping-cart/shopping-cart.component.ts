import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../shopping-cart.service';
import { ProductService } from '../product.service';
import {Product} from '../product';
import { Subscription } from 'rxjs';
import { Invoice } from '../invoice';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit {

  productsInCart:Product[] = [];
  invoiceGetSub: Subscription;
  invoice:Invoice;
  purchase:boolean = false;
  invoiceRecieved = false;

  constructor(private shoppingCartService: ShoppingCartService ) { }

  ngOnInit() {
    this.productsInCart = this.shoppingCartService.getAllShoppingCartProducts();
  }


  decreaseQuantityOnTheProductInShoppingCart(index: number){
    if(this.productsInCart[index].quantityToBuy>0)
       this.productsInCart[index].quantityToBuy--;
  }

  increaseQuantityOnTheProductInShoppingCart(index: number){
    if(this.productsInCart[index].quantityToBuy>0 && this.productsInCart[index].quantityToBuy <= this.productsInCart[index].quantity)
       this.productsInCart[index].quantityToBuy++;
  }

  onPurchase(){
    this.purchase = true;
    console.log("Invoking the service to calculate the receipt");

    this.invoiceGetSub = this.shoppingCartService.getReceipt().subscribe(
                          (result:Invoice)=>{
                                              this.invoice = result;
                                              this.invoiceRecieved = true;
                                              console.log("Invoice received");
                                              console.log(this.invoice);

                                              // Products have been ordered so clear the shopping cart
                                              this.shoppingCartService.clearShoppingCart();
                                            },
                                      (err)=>{
                                              console.log("Error in purchase");
                                              console.log(err);
                                            }
    )
  }



}
