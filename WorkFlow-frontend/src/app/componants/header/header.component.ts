import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../service/security/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(private authService: AuthService, private router: Router) {
  }

  isUserLogin(): boolean {
    return this.authService.isUserLogin();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }

  isCustomer(): boolean {
    return sessionStorage.getItem('role') === 'CUSTOMER';
  }

  protected readonly sessionStorage = sessionStorage;
}
