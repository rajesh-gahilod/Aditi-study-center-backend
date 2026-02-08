package co.Example.Study.Center.Entity;


import co.Example.Study.Center.Entity.Enums.BatchType;
import co.Example.Study.Center.Entity.Enums.Gender;
import co.Example.Study.Center.Entity.Enums.Transport_Mode;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

//    @Column(nullable = false, unique = true)
    private String email;
//    @Column(nullable = false)
    private String password;
    private String mobileNumber;
//    @Column(name = "dob")
//    private LocalDate Dob;
    @Column(name = "dob")
    private LocalDate dob;   // ✅ DB में DATE format store होगा
//    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int idNumber;
    private String what;

    // 🟢 Relation: Many students → One Batch
    @ManyToOne
    @JoinColumn(name = "batch_id", referencedColumnName = "id")
    private BatchInfo batchInfo;



    @Enumerated(EnumType.STRING)
    private Transport_Mode transportMode;

    private String vehicleNumber;
    private String address;


    private Integer resetOtp;
    private LocalDateTime otpExpiry;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Payment> payments;

//    private Long batchId;   // ✅ ONLY ID

    private String machineUserId;

}
