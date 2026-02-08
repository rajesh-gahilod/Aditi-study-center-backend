package co.Example.Study.Center.UserRepo;

import co.Example.Study.Center.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentId(Long studentId);

}
