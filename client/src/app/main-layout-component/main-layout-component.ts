import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {PageMenuComponent} from './page-menu-component/page-menu-component';
import {NgStyle} from '@angular/common';

@Component({
  selector: 'app-main-layout-component',
  imports: [
    RouterOutlet,
    PageMenuComponent,
    NgStyle
  ],
  templateUrl: './main-layout-component.html',
  styleUrl: './main-layout-component.css'
})
export class MainLayoutComponent {

  icons = [
    { svg: 'icon1', left: '16vw', top: '40vh' },
    { svg: 'icon2', left: '30vw', top: '15vh' },
    { svg: 'icon3', left: '85vw', top: '80vh' },
    { svg: 'icon4', left: '11vw', top: '70vh' },
    { svg: 'icon5', left: '70vw', top: '19vh' },
    { svg: 'icon6', left: '5vw', top: '9vh' },
    { svg: 'icon7', left: '93vw', top: '40vh' },
  ];

  // ngOnInit() {
  //   this.randomizeIcons();
  // }

  // randomizeIcons() {
  //   this.icons.forEach(icon => {
  //     icon.left = Math.random() * 90 + 'vw';
  //     icon.top = Math.random() * 80 + 'vh';
  //   });
  // }

}
