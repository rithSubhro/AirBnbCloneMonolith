import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-listing-card',
  templateUrl: './listing-card.component.html',
  styleUrls: ['./listing-card.component.scss']
})
export class ListingCardComponent {
  @Input() listing: any;

  constructor(private router: Router) {}

  open() {
    this.router.navigate(['/listings', this.listing.id]);
  }
}
