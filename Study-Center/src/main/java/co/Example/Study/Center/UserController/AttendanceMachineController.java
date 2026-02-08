package co.Example.Study.Center.UserController;

import co.Example.Study.Center.Entity.Attendance;
import co.Example.Study.Center.UserRepo.AttendanceRepository;
import co.Example.Study.Center.UserService.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machine")
@RequiredArgsConstructor
public class AttendanceMachineController {

        private final AttendanceService attendanceService;

        @PostMapping("/attendance")
        public ResponseEntity<String> receiveFromMachine(
                @RequestParam String userId,
                @RequestParam String time) {

            String response = attendanceService.markAttendance(userId, time);
            return ResponseEntity.ok(response);

        }
    private final AttendanceRepository attendanceRepo;

    @GetMapping("/admin/attendance")
    public List<Attendance> getAllAttendance() {
        return attendanceRepo.findAll();
    }
}

