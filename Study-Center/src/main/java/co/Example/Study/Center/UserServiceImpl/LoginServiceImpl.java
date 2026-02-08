//package co.Example.Study.Center.UserServiceImpl;
//
//
//import co.Example.Study.Center.Entity.Student;
//import co.Example.Study.Center.UserRepo.StudentRepository;
//import co.Example.Study.Center.UserService.LoginService;
//import co.Example.Study.Center.dto.LoginDTO;
//import co.Example.Study.Center.dto.StudentDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class LoginServiceImpl implements LoginService {
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private StudentServiceImpl studentServiceImpl; // to reuse entityToDto()
//
//    @Override
//    public ResponseEntity<?> loginStudent(StudentDTO loginDTO) {
//        try {
//            Optional<Student> studentOpt = studentRepository.findByEmail(loginDTO.getEmail());
//
//            if (studentOpt.isEmpty()) {
//                return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
//            }
//
//            Student student = studentOpt.get();
//
//            // Compare password using PasswordEncoder
//            if (!passwordEncoder.matches(loginDTO.getPassword(), student.getPassword())) {
//                return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
//            }
//
//            // Convert to DTO for dashboard
//            StudentDTO dto = studentServiceImpl.entityToDto(student);
//
//            return new ResponseEntity<>(dto, HttpStatus.OK);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>("Login failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
//
