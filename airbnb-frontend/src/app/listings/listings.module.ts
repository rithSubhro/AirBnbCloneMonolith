import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListingsRoutingModule } from './listings-routing.module';
import { HttpClientModule } from '@angular/common/http';

import { ListingsListComponent } from './pages/listings-list/listings-list.component';
import { ListingDetailsComponent } from './pages/listing-details/listing-details.component';
import { ListingCardComponent } from './components/listing-card/listing-card.component';

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { SharedModule } from '../shared/shared.module';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ListingsListComponent,
    ListingDetailsComponent,
    ListingCardComponent
  ],
  imports: [
    CommonModule,
    ListingsRoutingModule,
    HttpClientModule,
    MatCardModule,
    MatButtonModule,
    ReactiveFormsModule,
    SharedModule,
     MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    MatButtonModule
  ]
})
export class ListingsModule {}

