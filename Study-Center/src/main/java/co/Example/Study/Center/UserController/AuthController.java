package co.Example.Study.Center.UserController;

import co.Example.Study.Center.Config.JwtUtil;
import co.Example.Study.Center.Entity.Admin;
import co.Example.Study.Center.UserRepo.AdminRepo;
import co.Example.Study.Center.UserService.StudentService;
import co.Example.Study.Center.dto.LoginResponseDTO;
import co.Example.Study.Center.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/loginapi")
@CrossOrigin("*")
public class AuthController {


@Autowired
private StudentService studentService;

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/studentLogin")
    public LoginResponseDTO login(@RequestBody StudentDTO studentDTO) {
        return studentService.loginStudent(studentDTO);

    }



    // 🔐 Admin login with JWT
    @PostMapping("/adminlogin")
    public ResponseEntity<?> adminLogin(@RequestBody Admin loginDTO) {

        // 1️⃣ Check if admin exists
        Admin admin = adminRepo.findByUserAdmin(loginDTO.getUserAdmin())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // 2️⃣ Verify password
        if (!passwordEncoder.matches(
                loginDTO.getPasswordAdmin(),
                admin.getPasswordAdmin())) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid password"));
        }

        // 3️⃣ Generate JWT token
        String token = jwtUtil.generateToken(admin.getUserAdmin(), admin.getRole());

        // 4️⃣ Return response with token
        return ResponseEntity.ok(
                Map.of(
                        "username", admin.getUserAdmin(),
                        "role", admin.getRole(),
                        "token", token
                )
        );
    }

}
