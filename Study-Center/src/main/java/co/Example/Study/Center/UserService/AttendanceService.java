package co.Example.Study.Center.UserService;

import org.springframework.stereotype.Service;

@Service
public interface AttendanceService {
    String markAttendance(String machineUserId, String time);
}
