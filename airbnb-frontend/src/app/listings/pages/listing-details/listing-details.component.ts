import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { ListingsService } from '../../listings.service';
import { BookingApi } from 'src/app/core/booking/booking.api';
import { AuthService } from 'src/app/core/auth/auth.service';
declare var Razorpay: any;

@Component({
  selector: 'app-listing-details',
  templateUrl: './listing-details.component.html',
})
export class ListingDetailsComponent implements OnInit {
  listing: any;
  error = '';
  // Razorpay: any;

  form = this.fb.group({
    startDate: ['', Validators.required],
    endDate: ['', Validators.required],
  });

  constructor(
    private route: ActivatedRoute,
    private listings: ListingsService,
    private booking: BookingApi,
    private fb: FormBuilder,
    public auth: AuthService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.listings.getOne(id).subscribe((res) => (this.listing = res));
  }

  generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c) => {
      const r = (Math.random() * 16) | 0;
      const v = c === 'x' ? r : (r & 0x3) | 0x8;
      return v.toString(16);
    });
  }

  book() {
    if (this.form.invalid) return;

    const body = {
      listingId: this.listing.id,
      guestId: this.auth.getUserId(),
      startDate: this.form.value.startDate.toISOString().split('T')[0],
      endDate: this.form.value.endDate.toISOString().split('T')[0],
      totalPrice: this.listing.pricePerNight,
      // idempotencyKey: crypto.randomUUID(), TS +4.5 supports this
      idempotencyKey: this.generateUUID(),
    };

    this.booking.createBooking(body).subscribe((booking: any) => {
      const bookingId = booking.id;

      // 1. Create Razorpay order
      this.booking
        .createOrder(bookingId, booking.totalPrice)
        .subscribe((order: any) => {
          const options = {
            key: 'rzp_test_Rkl2YmRsk21Ifk', // replace with real key
            amount: order.amount,
            currency: 'INR',
            name: 'Airbnb Clone',
            description: 'Booking Payment',
            order_id: order.id,
            handler: (response: any) => {
              // 2. verify payment
              this.booking
                .verifyPayment(bookingId, response.razorpay_payment_id)
                .subscribe(() => {
                  alert('Payment success! Booking confirmed.');
                });
            },
            prefill: {
              email: 'test@example.com',
            },
          };

          const rzp = new Razorpay(options);
          rzp.open();
        });
    });
  }

}
