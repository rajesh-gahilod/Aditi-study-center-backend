package co.Example.Study.Center.UserService;

import co.Example.Study.Center.Entity.Student;
import co.Example.Study.Center.dto.LoginResponseDTO;
import co.Example.Study.Center.dto.StudentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    ResponseEntity<?> register(StudentDTO studentDTO);
StudentDTO updateStudent(Long id, StudentDTO studentDTO);
StudentDTO findById(Long id);
public ResponseEntity<?> studentGetByUsername(String username);
public LoginResponseDTO loginStudent(StudentDTO studentDTO);
Student findByEmail(String email);

    List<StudentDTO> findAll();
    public long getStudentCount();

    public Long getTotalFee() ;
}