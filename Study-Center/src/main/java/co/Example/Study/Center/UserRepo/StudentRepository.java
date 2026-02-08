package co.Example.Study.Center.UserRepo;

import co.Example.Study.Center.Entity.Student;
import co.Example.Study.Center.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // ✅ Corrected: returns entity, not DTO
    Optional<Student> findByUsername(String username);
    // Check existence
    boolean existsByIdNumber(int idNumber);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    // Find students by batch code
    List<Student> findByBatchInfo_BatchCode(String batchCode);
    Student findByEmail(String email);

    @Query("SELECT SUM(b.fee) FROM Student s JOIN s.batchInfo b")
    Long getTotalFeeCollected();

    Optional<Student> findByMachineUserId(String machineUserId);
}
