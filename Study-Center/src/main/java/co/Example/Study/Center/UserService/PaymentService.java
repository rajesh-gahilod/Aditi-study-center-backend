package co.Example.Study.Center.UserService;

import co.Example.Study.Center.dto.PaymentDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PaymentService {
    ResponseEntity<?> makePayment(PaymentDTO paymentDTO);
    ResponseEntity<?> getAllPayments();
    ResponseEntity<?> getPaymentsByStudent(Long studentId);

    // ✅ Create Razorpay Order
    ResponseEntity<?> createOrder(Map<String, Object> data);

    // ✅ Verify Payment
    ResponseEntity<?> verifyPayment(Map<String, String> data) throws Exception;
}