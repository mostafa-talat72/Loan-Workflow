import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ManagmentGuard implements CanActivate {
  constructor(private router: Router) {
  }
  canActivate(
      route: ActivatedRouteSnapshot,
      state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if(sessionStorage.getItem("role") !== "CUSTOMER")
      return true;

    this.router.navigateByUrl('/applications');
    return false;
  }
  
}
