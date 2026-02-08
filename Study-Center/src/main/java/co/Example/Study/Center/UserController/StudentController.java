package co.Example.Study.Center.UserController;

import co.Example.Study.Center.Entity.Admin;
import co.Example.Study.Center.Entity.Student;
import co.Example.Study.Center.UserRepo.AdminRepo;
import co.Example.Study.Center.UserRepo.StudentRepository;
import co.Example.Study.Center.UserService.StudentService;
import co.Example.Study.Center.UserServiceImpl.EmailService;
import co.Example.Study.Center.dto.LoginResponseDTO;
import co.Example.Study.Center.dto.StudentDTO;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody @Valid StudentDTO studentDTO) {

        return studentService.register(studentDTO);
    }

    @PutMapping("/studentsUpdate/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }



    @GetMapping("/StudentgetByName/{username}")
    public ResponseEntity<?> getByName(@PathVariable String username){
        try {
            return studentService.studentGetByUsername(username);
        }catch (Exception e){
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<StudentDTO> getUserById(@PathVariable Long id) {
        StudentDTO student = studentService.findById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {

        List<StudentDTO> students = studentService.findAll();

        if (students != null && !students.isEmpty()) {
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.noContent().build(); // 204
        }
    }



    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {

        System.out.println("FORGOT PASSWORD EMAIL RECEIVED: " + email);

        try {
            Student student = studentRepository.findByEmail(email);

            if (student == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Email not found");
            }

            // 2️⃣ Generate 6-digit OTP
            int otp = (int) (Math.random() * 900000) + 100000;

            // 3️⃣ Save OTP + Expiry
            student.setResetOtp(otp);
            student.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
            studentRepository.save(student);

            System.out.println("OTP SAVED IN DB: " + otp);

            // 4️⃣ Send Email (can comment if needed for test)
            emailService.sendEmail(
                    email,
                    "Password Reset OTP",
                    "Your OTP is: " + otp + " (valid for 10 minutes)"
            );

            System.out.println("EMAIL SENT SUCCESSFULLY");

            // 5️⃣ Success response
            return ResponseEntity.ok("OTP sent to email");

        } catch (Exception e) {


            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        Student student = studentRepository.findByEmail(email);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Email not found");
        }

        if (student.getResetOtp() == null ||
                !student.getResetOtp().toString().equals(otp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid OTP");
        }

        if (student.getOtpExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("OTP expired");
        }

        student.setPassword(passwordEncoder.encode(newPassword));
        student.setResetOtp(null);
        student.setOtpExpiry(null);

        studentRepository.save(student);

        return ResponseEntity.ok("Password reset successfully!");
    }




}

