import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-page-menu-component',
  imports: [
    RouterLink,
    NgOptimizedImage
  ],
  templateUrl: './page-menu-component.html',
  styleUrl: './page-menu-component.css'
})
export class PageMenuComponent {

}
