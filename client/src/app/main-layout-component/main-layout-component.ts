import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {PageMenuComponent} from './page-menu-component/page-menu-component';

@Component({
  selector: 'app-main-layout-component',
  imports: [
    RouterOutlet,
    PageMenuComponent
  ],
  templateUrl: './main-layout-component.html',
  styleUrl: './main-layout-component.css'
})
export class MainLayoutComponent {

}
