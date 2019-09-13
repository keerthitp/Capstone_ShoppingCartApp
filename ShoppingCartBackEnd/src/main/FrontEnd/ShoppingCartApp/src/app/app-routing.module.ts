import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {NavbarComponent} from './navbar/navbar.component';
import {ShoppingCartComponent} from './shopping-cart/shopping-cart.component'
import {AdminComponent} from './admin/admin.component'
import {ProductListComponent} from './product-list/product-list.component'
import {PageNotFoundComponent} from './page-not-found/page-not-found.component'


const routes: Routes = [
  {path:'products', component: ProductListComponent},
  {path:'cart', component: ShoppingCartComponent},
  {path: 'admin', component: AdminComponent},
  {path: '', redirectTo:'products', pathMatch:'full'},
  {path: '**', component: PageNotFoundComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
