package co.Example.Study.Center.UserController;


import co.Example.Study.Center.UserService.PaymentService;
import co.Example.Study.Center.dto.PaymentDTO;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:5500")

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/make-payment")
    public ResponseEntity<?> makePayment(@RequestBody PaymentDTO dto) {
        return paymentService.makePayment(dto);
    }

    // ✅ MARK: CREATE ORDER API
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(
            @RequestBody Map<String, Object> data) {

        return paymentService.createOrder(data);
    }

    // ✅ MARK: VERIFY PAYMENT API
    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(
            @RequestBody Map<String, String> data) throws Exception {

        return paymentService.verifyPayment(data);
    }
}

