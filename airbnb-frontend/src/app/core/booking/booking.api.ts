import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class BookingApi {
  constructor(private http: HttpClient) {}
  private BASE_URL = 'http://localhost:8080/api/bookings';

  createBooking(data: any) {
    return this.http.post(this.BASE_URL, data);
  }

  checkAvailability(listingId: string, start: string, end: string) {
    return this.http.get<boolean>(`${this.BASE_URL}/availability`, {
      params: { listingId, start, end },
    });
  }

  createOrder(bookingId: string, amount: number) {
    return this.http.post(
      `http://localhost:8080/api/payments/create-order?bookingId=${bookingId}&amount=${amount}`,
      {}
    );
  }

  verifyPayment(bookingId: string, paymentId: string) {
    return this.http.post(
      `http://localhost:8080/api/payments/verify?bookingId=${bookingId}&paymentId=${paymentId}`,
      {}
    );
  }
}
