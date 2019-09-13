import * as uuid from 'uuid';

export class Product{

    id:number;
    name:String;
    price:number;
    quantity:number;
    category:String;
    domesticOrImported:String;
    quantityToBuy?:number;
    imageUrl?:String;
    

    constructor(name:String, price:number, quantity:number, category:String, domesticOrImported:String,quantityToBuy?:number,imageUrl?:String){

        //this.id =1; // id will be assigned by the server
       // this.id = uuid.v4(); // Generates unique identifier for each Product that is added. But a new identifier is given in the server when added to database
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.domesticOrImported = domesticOrImported;
        this.quantityToBuy = quantityToBuy;
        this.imageUrl = imageUrl;
        

    }
}