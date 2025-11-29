import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListingsListComponent } from './pages/listings-list/listings-list.component';
import { ListingDetailsComponent } from './pages/listing-details/listing-details.component';
import { AuthGuard } from '../core/auth/auth.guard';

const routes: Routes = [
  { path: '', component: ListingsListComponent, canActivate: [AuthGuard] },
  { path: ':id', component: ListingDetailsComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ListingsRoutingModule {}
