package co.Example.Study.Center.UserServiceImpl;

import co.Example.Study.Center.Entity.Admin;
import co.Example.Study.Center.Entity.BatchInfo;
import co.Example.Study.Center.Entity.Student;
import co.Example.Study.Center.Exception.BatchNotFoundException;
import co.Example.Study.Center.Exception.InvalidCredentialsException;
import co.Example.Study.Center.Exception.InvalidInputException;
import co.Example.Study.Center.Exception.StudentAlreadyExistsException;
import co.Example.Study.Center.UserRepo.AdminRepo;
import co.Example.Study.Center.UserRepo.BatchRepo;
import co.Example.Study.Center.UserRepo.StudentRepository;
import co.Example.Study.Center.UserService.StudentService;
import co.Example.Study.Center.dto.LoginResponseDTO;
import co.Example.Study.Center.dto.StudentDTO;
import co.Example.Study.Center.mapper.StudentMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BatchRepo batchRepo;

    @Autowired
    private StudentMapper studentMapper;

    // ------------------- REGISTER -------------------
    @Override
    public ResponseEntity<?> register(StudentDTO studentDTO) {
        try {
            if (studentRepository.existsByIdNumber(studentDTO.getIdNumber())) {
                throw new StudentAlreadyExistsException("User with ID already exists");
            }

            if (studentRepository.existsByEmail(studentDTO.getEmail())) {
                throw new StudentAlreadyExistsException("Email already exists");
            }
            if (studentDTO.getPassword() == null || studentDTO.getPassword().trim().isEmpty()) {
                throw new InvalidInputException("Password is required");
            }

            if (studentDTO.getMobileNumber() == null || !studentDTO.getMobileNumber().matches("\\d{10}")) {
                throw new InvalidInputException("Mobile number must be 10 digits");
            }

            if (studentDTO.getBatchCode() == null || studentDTO.getBatchCode().trim().isEmpty()) {
                throw new BatchNotFoundException("Batch code is required");
            }

            Student student = studentMapper.toEntity(studentDTO);
            student.setPassword(passwordEncoder.encode(studentDTO.getPassword()));

            Student saved = studentRepository.save(student);
            return new ResponseEntity<>(studentMapper.toDto(saved), HttpStatus.CREATED);


        } catch (Exception e) {
            System.err.println("Error registering student: " + e.getMessage());
            throw e;
        }
    }

    // ------------------- UPDATE STUDENT -------------------
    @Override
    public StudentDTO updateStudent(Long id, StudentDTO dto) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        if (dto.getUsername() != null)student.setUsername(dto.getUsername());
        if (dto.getEmail() != null)student.setEmail(dto.getEmail());
        if (dto.getMobileNumber() != null)student.setMobileNumber(dto.getMobileNumber());

        if (dto.getBatchCode() != null && !dto.getBatchCode().trim().isEmpty()) {
            BatchInfo batchInfo = batchRepo
                    .findByBatchCode(dto.getBatchCode())
                    .orElseThrow(() -> new BatchNotFoundException(
                            "Batch not found with code: " + dto.getBatchCode()
                    ));

            student.setBatchInfo(batchInfo);
        }

        Student updatedStudent = studentRepository.save(student);
        return studentMapper.toDto(updatedStudent);
    }

    // ------------------- FIND BY ID -------------------
    @Override
    public StudentDTO findById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)  // ✅ Use mapper instance
                .orElse(null);
    }

    // ------------------- FIND BY USERNAME -------------------
    @Override
    public ResponseEntity<?> studentGetByUsername(String username) {
        try {
            Optional<Student> optionalStudent = studentRepository.findByUsername(username);

            if (optionalStudent.isPresent()) {
                // ✅ Call mapper instance
                return new ResponseEntity<>(studentMapper.toDto(optionalStudent.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Student with username " + username + " not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving student: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ------------------- LOGIN STUDENT -------------------
    @Override
    public LoginResponseDTO loginStudent(StudentDTO studentDTO) {

        if (studentDTO.getPassword() == null || studentDTO.getPassword().trim().isEmpty()) {
            throw new InvalidCredentialsException("Password is required");
        }

        Student student = studentRepository.findByEmail(studentDTO.getEmail());
        if (student == null) {
            throw new InvalidCredentialsException("Email not found");
        }

        if (!passwordEncoder.matches(studentDTO.getPassword(), student.getPassword())) {
            throw new InvalidCredentialsException("Incorrect password");
        }


        LoginResponseDTO response = new LoginResponseDTO();

        response.setId(student.getId());
        response.setUsername(student.getUsername());
        response.setEmail(student.getEmail());
        response.setMobileNumber(student.getMobileNumber());

        if (student.getBatchInfo() != null) {
            response.setBatchCode(student.getBatchInfo().getBatchCode());
            response.setFee(student.getBatchInfo().getFee());
            response.setTiming(student.getBatchInfo().getTiming());
        }

        return response;
    }


    public List<StudentDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(student -> studentMapper.toDto(student))
                .collect(Collectors.toList());
    }

    // ------------------- FIND BY EMAIL -------------------
    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }



    @Override
    public long getStudentCount() {
        return studentRepository.count();
    }

    @Override
    public Long getTotalFee() {
        return studentRepository.getTotalFeeCollected();
    }
}
