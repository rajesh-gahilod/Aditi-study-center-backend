package co.Example.Study.Center.dto;

import co.Example.Study.Center.Entity.BatchInfo;
import co.Example.Study.Center.Entity.Enums.BatchType;
import co.Example.Study.Center.Entity.Enums.Gender;
import co.Example.Study.Center.Entity.Enums.Transport_Mode;
import jakarta.validation.constraints.Email;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Digits;

import java.time.LocalDate;

@Data
public class StudentDTO {
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Invalid email format")
    private String email;

//    private BatchInfo batchInfo;

    // ⭐ ADD THIS
    private String batchCode;
    private Integer fee;
    private String timing;


    private String password;
    @NotNull(message = "Mobile number is required")
//    @Digits(integer = 10, fraction = 0, message = "Mobile number must be 10 digits")
    private String mobileNumber;


    private LocalDate dob;   // ✅ DB में DATE format store होगा
    @NotNull(message = "Gender is required")
    private Gender gender;
    @NotNull(message = "ID number is required")
    private Integer idNumber;
    private String what;
    @NotNull(message = "Transport mode is required")
    private Transport_Mode transportMode;
    private String vehicleNumber;
    @NotBlank(message = "Address must not be empty")
    private String address;


    // 🔥 Attendance machine se connect hone ke liye
    private String machineUserId;
}

