import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProductService } from '../product.service';
import { Subscription } from 'rxjs';
import { Product } from '../product';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit, OnDestroy {

  products: Product[] = [];

  itemGetSub: Subscription;

  searchbyName:string="";
  searchByCategory:string="";

  constructor(private productService: ProductService) { }

  ngOnInit() {

    this.getProducts();

    
  }

  getProducts(){
    this.itemGetSub = this.productService.getAllProductsFromServer().subscribe(
      (result:Product[])=>{
                            this.products = result;
                            console.log("Product list from server");
                            console.log(this.products);
                          },
                  (err)=>{
                            console.log(err);
                        }
    );
  }

  ngOnDestroy(){
    if(this.itemGetSub)
      this.itemGetSub.unsubscribe();

    
  }

  /* onNameChange(){
      if(this.searchbyName.length > 0){
        document.getElementById("searchByCategory").disabled=
      }
  }
 */
  /* onCategoryChange(){

  } */
}
