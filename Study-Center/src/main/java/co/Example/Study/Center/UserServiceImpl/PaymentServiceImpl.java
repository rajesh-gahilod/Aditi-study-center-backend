package co.Example.Study.Center.UserServiceImpl;

import co.Example.Study.Center.Entity.BatchInfo;
import co.Example.Study.Center.Entity.Payment;
import co.Example.Study.Center.Entity.Student;
import co.Example.Study.Center.UserRepo.BatchRepo;
import co.Example.Study.Center.UserRepo.PaymentRepo;
import co.Example.Study.Center.UserRepo.StudentRepository;
import co.Example.Study.Center.UserService.PaymentService;
import co.Example.Study.Center.dto.PaymentDTO;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BatchRepo batchRepo;








    @Override
    public ResponseEntity<?> makePayment(PaymentDTO paymentDTO) {

        Student student = studentRepository.findById(paymentDTO.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found with ID: " + paymentDTO.getStudentId()));

        BatchInfo batch = batchRepo.findByBatchCode(paymentDTO.getBatchCode())
                .orElseThrow(() ->
                        new RuntimeException("Batch not found with Code: " + paymentDTO.getBatchCode()));

        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setBatchInfo(batch);
        payment.setAmount(batch.getFee());
        payment.setPaymentMode(paymentDTO.getPaymentMode());
        payment.setStatus("SUCCESS");

        Payment savedPayment = paymentRepo.save(payment);

        System.out.println("PAYMENT SAVED ID = " + savedPayment.getId());

        return ResponseEntity.ok(savedPayment);
    }


    // ✅ Make Payment
    @Value("${razorpay.key}")
    private String key;
    @Value("${razorpay.secret}")
    private String secret;

    // ✅ MARK: CREATE ORDER LOGIC
    @Override
    public ResponseEntity<?> createOrder(Map<String, Object> data) {
        try {
            int amount = Integer.parseInt(data.get("amount").toString());

            RazorpayClient client = new RazorpayClient(key, secret);

            JSONObject options = new JSONObject();
            options.put("amount", amount * 100); // paise
            options.put("currency", "INR");
            options.put("receipt", "txn_" + System.currentTimeMillis());

            Order order = client.orders.create(options);

            // ✅ JSON response
            return ResponseEntity.ok(order.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Order creation failed",
                            "message", e.getMessage()
                    ));
        }
    }

    // ✅ MARK: VERIFY PAYMENT LOGIC
    @Override
    public ResponseEntity<?> verifyPayment(Map<String, String> data) throws Exception {

        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String signature = data.get("razorpay_signature");

        String generatedSignature =
                hmacSHA256(orderId + "|" + paymentId, secret);

        if (generatedSignature.equals(signature)) {
            return ResponseEntity.ok("Payment Verified Successfully");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Payment Verification Failed");
        }
    }

    // 🔐 MARK: SIGNATURE METHOD
    private String hmacSHA256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey =
                new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        mac.init(secretKey);
        byte[] hash = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }









    @Override
    public ResponseEntity<?> getAllPayments() {
        return ResponseEntity.ok(paymentRepo.findAll());
    }

    @Override
    public ResponseEntity<?> getPaymentsByStudent(Long studentId) {
        return null;
    }
}

