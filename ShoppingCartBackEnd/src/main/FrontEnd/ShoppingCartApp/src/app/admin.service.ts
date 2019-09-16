import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  authenticated = true;

  constructor() { }

  isAuthenticated(){
    return this.authenticated
  }
}
