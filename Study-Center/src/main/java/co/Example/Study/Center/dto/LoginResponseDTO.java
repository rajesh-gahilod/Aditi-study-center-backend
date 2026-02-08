package co.Example.Study.Center.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private Long id; // ✅ ADD THIS
    private String username;
    private String email;
    private String mobileNumber;

    private String batchCode;
    private Integer fee;
    private String timing;
}
