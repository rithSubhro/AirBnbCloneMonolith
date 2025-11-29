package com.airbnbclone.backend.service;

import com.airbnbclone.backend.entity.Booking;
import com.airbnbclone.backend.repository.BookingRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final RazorpayClient razorpay;

    @Value("${razorpay.key}")
    private String keyId;

    @Value("${razorpay.secret}")
    private String keySecret;

    public String createOrder(String bookingId, int amount) throws Exception {
        System.out.println(keyId+" "+keySecret);
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);  // Razorpay uses paisa
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", bookingId);
        orderRequest.put("payment_capture", 1);

        Order order = client.orders.create(orderRequest);

        return order.toString(); // full order JSON
    }

    public void verifyPayment(Map<String, String> body) {
        String razorpayOrderId = body.get("razorpay_order_id");
        String razorpayPaymentId = body.get("razorpay_payment_id");
        String razorpaySignature = body.get("razorpay_signature");
        String bookingIdStr = body.get("bookingId");

        if (razorpayOrderId == null || razorpayPaymentId == null || razorpaySignature == null || bookingIdStr == null) {
            throw new RuntimeException("Missing payment verification fields");
        }

        String payload = razorpayOrderId + "|" + razorpayPaymentId;
        boolean valid = validateSignature(payload, razorpaySignature, keySecret);
        if (!valid) {
            throw new RuntimeException("Invalid payment signature");
        }

        UUID bookingId = UUID.fromString(bookingIdStr);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setPaymentId(razorpayPaymentId);
        booking.setStatus("CONFIRMED");
        booking.setUpdatedAt(java.time.Instant.now());
        bookingRepository.save(booking);
    }

    private boolean validateSignature(String payload, String signature, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secretKey);
            byte[] hash = sha256_HMAC.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            String actual = bytesToHex(hash);
            return actual.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }
}
