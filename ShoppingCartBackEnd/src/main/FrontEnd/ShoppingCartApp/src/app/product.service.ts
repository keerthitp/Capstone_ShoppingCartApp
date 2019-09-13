import { Injectable } from '@angular/core';
import {Product} from './product';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

//products: Product[] = []; // Array to store all the products from the database

serverURL:String = "http://localhost:8081"; // 

  constructor(private http: HttpClient) { }

  getAllProductsFromServer() : Observable<Product[]> {

    const url = `${this.serverURL}/product`
    return this.http.get<Product[]>(url);
  }

  addProductToServer(product:Product ):Observable<Product>{
    const url = `${this.serverURL}/product`;
    return this.http.post<Product>(url, product);
  }

  updateProductInServer(product: Product): Observable<Product>{
    const url = `${this.serverURL}/product`;
    return this.http.put<Product>(url, product);
  }

  updatePartialProductInServer(product:Product): Observable<Product>{
    const url =  `${this.serverURL}/product`;
    return this.http.patch<Product>(url,product);
  }

  getProductById(id: number): Observable<Product>{
  return of(null);
  }

  deleteProductInServer(productId:number):Observable<Product>{
    const url =  `${this.serverURL}/product/${productId}`;
    return this.http.delete<Product>(url);
  }
}
