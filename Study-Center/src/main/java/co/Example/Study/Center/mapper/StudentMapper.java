package co.Example.Study.Center.mapper;


import co.Example.Study.Center.Entity.BatchInfo;
import co.Example.Study.Center.Entity.Student;
import co.Example.Study.Center.UserRepo.BatchRepo;
import co.Example.Study.Center.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentMapper {

    @Autowired
    private BatchRepo batchRepo;
    // DTO → Entity

//    Jab client se data aata hai (JSON → DB)
//    ✔ Register student
//    ✔ Update student
//    Save/update → toEntity()
    public Student toEntity(StudentDTO dto) {
        if (dto == null) return null;

        Student student = new Student();
        student.setId(dto.getId());
        student.setUsername(dto.getUsername());
        student.setEmail(dto.getEmail());
        student.setPassword(dto.getPassword()); // ✅ REQUIRED
        student.setMobileNumber(dto.getMobileNumber());
        student.setGender(dto.getGender());
        student.setIdNumber(dto.getIdNumber());
        student.setWhat(dto.getWhat());
        student.setDob(dto.getDob());
        student.setAddress(dto.getAddress());
        student.setVehicleNumber(dto.getVehicleNumber());
        student.setTransportMode(dto.getTransportMode());
        student.setMachineUserId(dto.getMachineUserId());

        // ✅ MUST EXIST
        if (dto.getBatchCode() != null) {
            BatchInfo batch = batchRepo.findByBatchCode(dto.getBatchCode())
                    .orElseThrow(() -> new RuntimeException("Invalid batch code"));
            student.setBatchInfo(batch);
        }



        return student;
    }

//    ab database se data bahar bhejna hota hai (DB → Response)
//            ✔ Login response
//            ✔ Get student by id
//            ✔ List students
//          Show/response → toDto()
    public StudentDTO toDto(Student student) {
        if (student == null) return null;

        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setUsername(student.getUsername());
        dto.setEmail(student.getEmail());
        dto.setMobileNumber(student.getMobileNumber());
        dto.setGender(student.getGender());
        dto.setIdNumber(student.getIdNumber());
        dto.setWhat(student.getWhat());
        dto.setDob(student.getDob());
        dto.setAddress(student.getAddress());
        dto.setVehicleNumber(student.getVehicleNumber());
        dto.setTransportMode(student.getTransportMode());

        dto.setPassword(null); // ❌ Password should not be sent

        dto.setMachineUserId(student.getMachineUserId());
        // 🔹 Map batch info if present
        if (student.getBatchInfo() != null) {
            BatchInfo batch = student.getBatchInfo();
            dto.setBatchCode(batch.getBatchCode());
            dto.setFee(batch.getFee());
            dto.setTiming(batch.getTiming());
        }




        return dto;
    }
}


