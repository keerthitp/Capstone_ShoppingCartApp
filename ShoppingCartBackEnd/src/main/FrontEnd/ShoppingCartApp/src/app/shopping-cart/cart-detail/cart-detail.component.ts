import { Component, OnInit, Input } from '@angular/core';
import { Product } from '../../product';
import { ProductService } from 'src/app/product.service';
import { ShoppingCartService } from 'src/app/shopping-cart.service';

@Component({
  selector: 'app-cart-detail',
  templateUrl: './cart-detail.component.html',
  styleUrls: ['./cart-detail.component.scss']
})
export class CartDetailComponent implements OnInit {

  @Input() product:Product;
  @Input() i:number;
  
  constructor(private shoppingCartService: ShoppingCartService) { }

  ngOnInit() {

    console.log("Image:" + this.product.imageUrl)
    console.log(this.product);
  }

   decreaseQuantityOnTheProductInShoppingCart(index:number){

    this.shoppingCartService.decreaseQuantityOnTheProductInShoppingCart(index);
     
    this.shoppingCartService.getAllShoppingCartProducts();
        // this.getAllShoppingCartProducts();
        //  this.calculateTotal();
  }

  increaseQuantityOnTheProductInShoppingCart(index:number){

    this.shoppingCartService.increaseQuantityOnTheProductInShoppingCart(index);
    this.shoppingCartService.getAllShoppingCartProducts();
 
    /* if(this.productsInCart[index].quantityToBuy>0 && this.productsInCart[index].quantityToBuy <= this.productsInCart[index].quantity)
       this.productsInCart[index].quantityToBuy++;

       

       this.getAllShoppingCartProducts();
       this.calculateTotal(); */
  }
  
       onRemoveProductFromCart(index: number){
         this.shoppingCartService.removeProductFromCart(index);
       }


}
