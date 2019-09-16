import {
    Component,
    Input
  } from '@angular/core';
  
  @Component({
    selector: 'svg-icon',
    template: `
    <svg>
    <use xlink:href="../../assets/icons/symbol-defs.svg#icon-images"></use>
  </svg>
    `
  })
  export class SvgIconComponent {
    @Input() icon: string;
  }