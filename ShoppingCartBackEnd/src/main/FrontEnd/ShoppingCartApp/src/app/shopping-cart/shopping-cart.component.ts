import { Component, OnInit, OnDestroy } from '@angular/core';
import { ShoppingCartService } from '../shopping-cart.service';
import { ProductService } from '../product.service';
import {Product} from '../product';
import { Subscription } from 'rxjs';
import { Invoice } from '../invoice';
import {CartDetailComponent} from './cart-detail/cart-detail.component'

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit, OnDestroy {

  productsInCart:Product[] = [];
  invoiceGetSub: Subscription;
  invoice:Invoice;
  purchase:boolean = false;
  invoiceRecieved = false;
  total:number = 0;

  constructor(private shoppingCartService: ShoppingCartService ) { }

  ngOnInit() {
    this.productsInCart = this.getAllShoppingCartProducts(); //this.shoppingCartService.getAllShoppingCartProducts();
    this.calculateTotal();
  }


  decreaseQuantityOnTheProductInShoppingCart(index: number){
    if(this.productsInCart[index].quantityToBuy>0)
       this.productsInCart[index].quantityToBuy--;

       if(this.productsInCart[index].quantityToBuy == 0)
        this.shoppingCartService.removeProductFromCart(index);
      
        this.getAllShoppingCartProducts();
       this.calculateTotal();
       this.shoppingCartService.calculateCartItems();
  }

  increaseQuantityOnTheProductInShoppingCart(index: number){
    if(this.productsInCart[index].quantityToBuy>0 && this.productsInCart[index].quantityToBuy < this.productsInCart[index].quantity)
       this.productsInCart[index].quantityToBuy++;

       

       this.getAllShoppingCartProducts();
       this.calculateTotal();
       this.shoppingCartService.calculateCartItems();
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
                                              this.shoppingCartService.calculateCartItems();
                                            },
                                      (err)=>{
                                              console.log("Error in purchase");
                                              console.log(err);
                                            }
    )
  }

  onRemoveProductFromCart(index:number){
    this.shoppingCartService.removeProductFromCart(index);
    this.getAllShoppingCartProducts();
    this.calculateTotal();
    this.shoppingCartService.calculateCartItems();
    
  }

  getAllShoppingCartProducts(){
    return this.shoppingCartService.getAllShoppingCartProducts();
  }
  
  calculateTotal(){
    this.total = 0;
    this.getAllShoppingCartProducts().forEach(product=>{

              this.total += (  product.price * product.quantityToBuy)
              console.log(this.total);
    })
  }

ngOnDestroy(){
  if(this.invoiceGetSub)
    this.invoiceGetSub.unsubscribe();
}

}
