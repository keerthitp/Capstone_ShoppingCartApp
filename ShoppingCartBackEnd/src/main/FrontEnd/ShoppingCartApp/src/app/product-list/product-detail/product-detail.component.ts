import { Component, OnInit, Input } from '@angular/core';
import { Product } from 'src/app/product';
import { ShoppingCartService } from 'src/app/shopping-cart.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss']
})
export class ProductDetailComponent implements OnInit {

  @Input() product:Product;
  defaultQuantityToBuy =1;
  quantityToBuy:number =1 ;

  productToAddToTheShoppingCart:Product;

  constructor(private shoppingCartService: ShoppingCartService) { }

  ngOnInit() {
  }

  onAddProductToCart(product){

    
    if(this.quantityToBuy > 0 && this.quantityToBuy <= product.quantity ){

      
      this.productToAddToTheShoppingCart = new Product(product.name,product.price,product.quantity,product.category,product.domesticOrImported,this.quantityToBuy, product.imageUrl);
      this.productToAddToTheShoppingCart.id = product.id;
      this.shoppingCartService.addProductToShoppingCart(this.productToAddToTheShoppingCart);
    }
    /* else
       this.quantityToBuy =1; */
  }

}
