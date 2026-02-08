package co.Example.Study.Center.UserController;

import co.Example.Study.Center.Config.JwtUtil;
import co.Example.Study.Center.Entity.Admin;
import co.Example.Study.Center.UserRepo.AdminRepo;
import co.Example.Study.Center.UserService.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/adminApi")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {
//       ├── AdminController
// │     ├── createAdmin
// │     ├── studentCount
// │     └── totalFee


    private final StudentService studentService;
    private final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ✅ CREATE ADMIN (ONE TIME)
    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin() {

        Optional<Admin> existingAdmin =
                adminRepo.findByUserAdmin("admin@gmail.com");

        if (existingAdmin.isPresent()) {
            return ResponseEntity.ok("Admin already exists");
        }

        Admin admin = new Admin();
        admin.setUserAdmin("admin@gmail.com");
        admin.setPasswordAdmin(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");

        adminRepo.save(admin);

        return ResponseEntity.ok("Admin Created Successfully");
    }

    @GetMapping("/studentCount")
    public ResponseEntity<Long> getStudentCount() {
        return ResponseEntity.ok(studentService.getStudentCount());
    }

    @GetMapping("/totalFee")
    public ResponseEntity<Long> getTotalFee() {
        return ResponseEntity.ok(studentService.getTotalFee());
    }




}
