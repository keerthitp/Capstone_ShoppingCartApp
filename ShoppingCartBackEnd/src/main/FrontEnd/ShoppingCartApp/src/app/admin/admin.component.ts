import { Component, OnInit, OnDestroy } from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Product} from '../product';
import { ProductService } from '../product.service';





@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit,OnDestroy {

product:Product;
products:Product[] = [];
productToEdit:Product=undefined;
editId:number;

addForm = this.fb.group({


  name: [''],
  price:[],
  category:[''],
  quantity:[],
  domesticOrImported:[''],
  quantityToBuy:[],
  imageUrl:['']


}
);

addProductSub:Subscription;
deleteProductSub:Subscription;
putSub:Subscription;
uodatePartialProductSub:Subscription;
getProductsSub:Subscription;

  constructor(private fb: FormBuilder, private productService: ProductService) { }

  ngOnInit() {
    console.log("Admin is loading and getting all the products from Server")
    this.getAllProducts();
  }

  onSubmit(){

    // getting the values from the reactive forms
    
    const name = this.addForm.value.name;
    const price = this.addForm.value.price;
    const quantity = this.addForm.value.quantity;
    const category = this.addForm.value.category;
    const domesticOrImported = this.addForm.value.domesticOrImported;
    const imageUrl = this.addForm.value.imageUrl;
    const quantityToBuy = 0;
    let editId=0;

    if(this.productToEdit == undefined){ // product being added
                    const productToAdd:Product = new Product(name, price, quantity, category, domesticOrImported,0,imageUrl);

                    
                    console.log("Product to add");
                    console.log(productToAdd);

                    this.addProductSub = this.productService.addProductToServer(productToAdd).subscribe(
                        (res:Product)=>{
                                          console.log("Product added by admin has been added to database");
                                          this.product = res;
                                          console.log(this.product);
                                          //refresh the productListSection with the newly added product
                                          this.onResetForm();

                                          this.getAllProducts();
                                      },
                        err=>         {
                                          console.log(err);
                                      }

                    );

                    console.log("Product to add");
      }
      else {
              // product being edited

              this.productToEdit.name = name;
              this.productToEdit.category = category;
              this.productToEdit.domesticOrImported = domesticOrImported;
              this.productToEdit.price = price;
              this.productToEdit.quantity = quantity;
              this.productToEdit.imageUrl=imageUrl;

              this.putSub = this.productService.updateProductInServer(this.productToEdit).subscribe(
                                  (result:Product)=>{
                                    console.log("Product updated");
                                    console.log(result);
                                    this.onResetForm();
                                    this.productToEdit=undefined;
                                    this.editId = 0;
                                    this.getAllProducts();
                                  },
                                  err=>{
                                          console.log(err);
                                  }
                              );


             


           }

  }

  getAllProducts(){
    this.getProductsSub = this.productService.getAllProductsFromServer().subscribe(

        (result:Product[])=>{
                                this.products = result;
                                console.log("Admin: Product list from the server");
                                console.log(this.products);
                                //this.productService();
                            },
                      err=>{
                                console.log(err);

                             } );
  }

  onResetForm(){
    this.addForm.reset();
  }

  ngOnDestroy(){
    if(this.addProductSub)
      this.addProductSub.unsubscribe();
    
    if(this.getProductsSub)
     this.getProductsSub.unsubscribe();

    if(this.putSub)
      this.getProductsSub.unsubscribe();

    if(this.deleteProductSub)
       this.deleteProductSub.unsubscribe();
    
    if(this.uodatePartialProductSub)
      this.uodatePartialProductSub.unsubscribe();
    
    
    }

    onEdit(id:number){
      this.editId=id;
      this.getAllProducts();
      this.products.map(
                            p => {
                              if(p.id == this.editId){
                                this.productToEdit = p;
                                return;
                              }
                            }
                        );
      this.addForm.patchValue(this.productToEdit);
      
    }

    onDelete(id:number){
      console.log("To delete: id: "+ id);
      console.log("Delete request sent");
       this.deleteProductSub = this.productService.deleteProductInServer(id).subscribe(
         (res:any)=>{
           console.log("Deleted");
           this.getAllProducts();
         }
       );
       
    }
  }


