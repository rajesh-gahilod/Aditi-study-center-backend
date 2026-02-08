package co.Example.Study.Center.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.jdbc.batch.spi.Batch;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Machine se aane wala ID
    private String machineUserId;

    private LocalDate attendanceDate;
    private LocalTime attendanceTime;

    private String status; // IN / OUT

    private String source; // BIOMETRIC / MANUAL

    // Already existing Student
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Already existing Batch
//    @ManyToOne
//    @JoinColumn(name = "batch_id")
//    private Batch batch;
}

