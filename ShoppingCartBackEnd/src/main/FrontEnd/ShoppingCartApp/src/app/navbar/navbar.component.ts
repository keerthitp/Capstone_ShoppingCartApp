import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  title:String = "Shopping Cart App";

  searchByName:String='';
  searchByCategory:String='';

  constructor() { }

  ngOnInit() {
  }

}
