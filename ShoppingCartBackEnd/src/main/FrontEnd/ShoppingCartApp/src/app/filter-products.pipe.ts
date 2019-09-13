import { Pipe, PipeTransform } from '@angular/core';
import { Product } from './product';

@Pipe({
  name: 'filterProducts'
})
export class FilterProductsPipe implements PipeTransform {

  transform(value: Product[], searchbyName:String, searchByCategory: String): Product[] {

    if (searchbyName.length > 0 && searchByCategory.length > 0){
      return value.filter(product=>product.category.toLowerCase().includes(searchByCategory.toLowerCase()))
                  .filter(product=>product.name.toLowerCase().includes(searchbyName.toLowerCase()));
    }
    else
    if(searchByCategory.length > 0 && searchbyName.length == 0){
      return value.filter(product=>product.category.toLowerCase().includes(searchByCategory.toLowerCase()));
    }
    else 
    if (searchbyName.length > 0 || searchByCategory.length == 0){
      return value.filter(product=>product.name.toLowerCase().includes(searchbyName.toLowerCase()));
    }
    
    else
    return null;


    // switch(searchBy.toLowerCase()){
    //   case "name":
        
        
    //   case "category":
        
        
    //   default:
    //     console.log("Invalid filtering")
    //     return null;
    // }
  }

}
