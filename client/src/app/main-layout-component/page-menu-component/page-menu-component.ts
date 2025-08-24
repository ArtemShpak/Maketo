import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';
import {BurgerMenuComponent} from './burger-menu-component/burger-menu-component';

@Component({
  selector: 'app-page-menu-component',
  imports: [
    RouterLink,
    NgOptimizedImage,
    BurgerMenuComponent
  ],
  templateUrl: './page-menu-component.html',
  styleUrls: ['./page-menu-component.css', '../../../styles/theme.css']
})
export class PageMenuComponent {

}
