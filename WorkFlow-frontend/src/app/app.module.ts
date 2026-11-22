import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { HeaderComponent } from './componants/header/header.component';
import { LoginComponent } from './componants/login/login.component';
import { SignupComponent } from './componants/signup/signup.component';
import { MyApplicationsComponent } from './componants/my-applications/my-applications.component';
import { ApplicationFormComponent } from './componants/application-form/application-form.component';
import { ApplicationDetailsComponent } from './componants/application-details/application-details.component';
import { PendingApplicationsComponent } from './componants/pending-applications/pending-applications.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthInterceptor} from "../interceptor/auth.interceptor";
import {AuthGuard} from "../guard/auth.guard";
import {LoginSignupGuard} from "../guard/login-signup.guard";
import {CustomerGuard} from "../guard/customer.guard";
import {ManagmentGuard} from "../guard/managment.guard";

export const routes: Routes = [

  // http://localhost:4200/login
  { path: 'login', component: LoginComponent, canActivate:[LoginSignupGuard] },
  { path: 'signup', component: SignupComponent, canActivate:[LoginSignupGuard]},

  // Customer screens
  { path: 'applications', component: MyApplicationsComponent, canActivate:[AuthGuard, CustomerGuard] },
  { path: 'applications/new', component: ApplicationFormComponent, canActivate:[AuthGuard, CustomerGuard] },

  // Customer sees own applications; officers can open any application
  { path: 'applications/:id', component: ApplicationDetailsComponent, canActivate:[AuthGuard] },

  // Shared work queue for the two review roles
  { path: 'pending', component: PendingApplicationsComponent, canActivate:[AuthGuard, ManagmentGuard] },

  { path: '', redirectTo: '/applications', pathMatch: 'full' },

  { path: '**', redirectTo: '/applications', pathMatch: 'full' }
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    SignupComponent,
    MyApplicationsComponent,
    ApplicationFormComponent,
    ApplicationDetailsComponent,
    PendingApplicationsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
