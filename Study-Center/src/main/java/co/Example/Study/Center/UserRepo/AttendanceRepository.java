package co.Example.Study.Center.UserRepo;

import co.Example.Study.Center.Entity.Attendance;
import co.Example.Study.Center.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByAttendanceDate(LocalDate date);
    boolean existsByStudentAndAttendanceDate(Student student, LocalDate date);
}
