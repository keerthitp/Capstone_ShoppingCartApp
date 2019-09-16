import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminComponent } from './admin/admin.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from './navbar/navbar.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { ProductListComponent } from './product-list/product-list.component';
import {ProductDetailComponent} from './product-list/product-detail/product-detail.component';

import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

import { DigitOnlyModule } from '@uiowa/digit-only';
import { FilterProductsPipe } from './filter-products.pipe';
import { CartDetailComponent } from './shopping-cart/cart-detail/cart-detail.component';
import { SvgIconComponent } from './svg-icon.component';
import { FrontPageComponent } from './front-page/front-page.component';
import { FooterComponent } from './footer/footer.component';




@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    NavbarComponent,
    ShoppingCartComponent,
    ProductListComponent,
    ProductDetailComponent,
    
    PageNotFoundComponent,
    
    FilterProductsPipe,
    CartDetailComponent,
    
    
    SvgIconComponent,
    
    
    FrontPageComponent,
    
    
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    DigitOnlyModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
