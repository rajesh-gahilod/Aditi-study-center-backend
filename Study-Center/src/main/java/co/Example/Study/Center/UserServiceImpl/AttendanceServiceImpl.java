package co.Example.Study.Center.UserServiceImpl;

import co.Example.Study.Center.Entity.Attendance;
import co.Example.Study.Center.Entity.Student;
import co.Example.Study.Center.UserRepo.AttendanceRepository;
import co.Example.Study.Center.UserRepo.StudentRepository;
import co.Example.Study.Center.UserService.AttendanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final StudentRepository studentRepo;
    private final AttendanceRepository attendanceRepo;

    @Transactional
    @Override
    public String markAttendance(String machineUserId, String time) {

        // 1️⃣ Student find using machineUserId
        Student student = studentRepo.findByMachineUserId(machineUserId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        LocalDate today = LocalDate.now();

        // 2️⃣ Duplicate check
        if (attendanceRepo.existsByStudentAndAttendanceDate(student, today)) {
            return "Attendance already marked";
        }

        // 3️⃣ Attendance save
        Attendance attendance = new Attendance();
        attendance.setMachineUserId(machineUserId);
        attendance.setAttendanceDate(today);
        attendance.setAttendanceTime(LocalTime.parse(time));
        attendance.setStatus("IN");
        attendance.setStudent(student);
//        attendance.setBatch(student.getBatchInfo().getBatchCode()); // batch auto link

        attendanceRepo.save(attendance);

        return "Attendance Marked Successfully";
    }
}

