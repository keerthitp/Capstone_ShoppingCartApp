import { Component, OnInit } from '@angular/core';
import { ShoppingCartService } from '../shopping-cart.service';
import { Product } from '../product';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  title:String = "Around the world!";

  cartProducts: Product[];

  searchByName:String='';
  searchByCategory:String='';
  
  
  numberOfProductsInCart:number;
  total = 0;
   message:number;

  constructor(private cartService : ShoppingCartService) { }

  ngOnInit() {

    this.cartService.currentMessage.subscribe(message => this.total = message);

  //  this.cartService.currentTotal.subscribe(total=>this.total=total);

    /* this.cartProducts = this.cartService.getAllShoppingCartProducts();  
    let j =0; this.numberOfProductsInCart = 0;

    for(j=0; j< this.cartProducts.length; j++){
        this.numberOfProductsInCart += this.cartProducts[j].quantityToBuy; */

    }
  }


