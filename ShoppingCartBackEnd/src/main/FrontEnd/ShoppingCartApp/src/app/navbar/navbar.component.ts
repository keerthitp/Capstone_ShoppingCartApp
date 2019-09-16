import { Component, OnInit, OnDestroy } from '@angular/core';
import { ShoppingCartService } from '../shopping-cart.service';
import { Product } from '../product';
import { Subscription } from 'rxjs';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {

  title:String = "Around the world!";

  cartProducts: Product[];

  searchByName:String='';
  searchByCategory:String='';
  
  
  numberOfProductsInCart:number;
  total = 0;
   message:number;

   numberOfCartProductSubscription: Subscription;

  constructor(private cartService : ShoppingCartService, private adminService: AdminService) { }

  ngOnInit() {

    this.numberOfCartProductSubscription=this.cartService.currentMessage.subscribe(message => this.total = message);

  //  this.cartService.currentTotal.subscribe(total=>this.total=total);

    /* this.cartProducts = this.cartService.getAllShoppingCartProducts();  
    let j =0; this.numberOfProductsInCart = 0;

    for(j=0; j< this.cartProducts.length; j++){
        this.numberOfProductsInCart += this.cartProducts[j].quantityToBuy; */

    }

  ngOnDestroy(){
    if(this.numberOfCartProductSubscription)
      this.numberOfCartProductSubscription.unsubscribe();
  }

  onToggleAuthenticated(){
      this.adminService.authenticated = !this.adminService.authenticated;
  }
  }



