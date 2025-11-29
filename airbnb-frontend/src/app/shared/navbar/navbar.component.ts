import { Component } from '@angular/core';
import { AuthService } from '../../core/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent {
  constructor(public auth: AuthService, private router: Router) {}

  applySearch(filters: any) {
    console.log('Search triggered from navbar:', filters);
    // Later we will route to listings with filters
    this.router.navigate(['/listings'], { queryParams: filters });
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/auth/login']);
  }
}
