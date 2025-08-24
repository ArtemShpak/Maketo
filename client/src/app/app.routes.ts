import { Routes } from '@angular/router';
import {MainLayoutComponent} from './main-layout-component/main-layout-component';
import {WelcomePageComponent} from './main-layout-component/welcome-page-component/welcome-page-component';
import {AuthLayoutComponent} from './auth-layout-component/auth-layout-component';
import {LoginPageComponent} from './auth-layout-component/login-page-component/login-page-component';
import {RegisterPageComponent} from './auth-layout-component/register-page-component/register-page-component';
import {AboutPageComponent} from './main-layout-component/about-page-component/about-page-component';
import {UserPageComponent} from './user-page-component/user-page-component';
import {ProfilePageComponent} from './user-page-component/profile-page-component/profile-page-component';

export const routes: Routes = [
  {
    path: 'maketo',
    component: MainLayoutComponent,
    children: [
      { path: '', component: WelcomePageComponent },
      { path: 'about', component: AboutPageComponent },
    ]
  },
  {
    path: 'maketo',
    component: AuthLayoutComponent,
    children: [
      { path: 'login', component: LoginPageComponent },
      { path: 'register', component: RegisterPageComponent }
    ]
  },
  {
    path: 'maketo',
    component: UserPageComponent,
    children: [
      { path: 'profile', component: ProfilePageComponent },
    ]
  }
];
