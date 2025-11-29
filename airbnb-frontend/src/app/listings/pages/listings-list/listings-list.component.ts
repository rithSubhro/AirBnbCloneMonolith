import { Component, OnInit } from '@angular/core';
import { ListingsService } from '../../listings.service';

@Component({
  selector: 'app-listings-list',
  templateUrl: './listings-list.component.html',
  styleUrls: ['./listings-list.component.scss']
})
export class ListingsListComponent implements OnInit {

  listings: any[] = [];
  filteredListings: any[] = [];
  loading = true;

  constructor(private api: ListingsService) {}

  ngOnInit() {
    this.api.getAll().subscribe({
      next: (res) => {
        this.listings = res;
        this.filteredListings = res;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  applySearch(filters: any) {
    console.log('Filters:', filters);

    const { location, guests } = filters;

    this.filteredListings = this.listings.filter(l =>
      (!location || l.address.toLowerCase().includes(location.toLowerCase())) &&
      (!guests || guests >= 1)
    );
  }
}
