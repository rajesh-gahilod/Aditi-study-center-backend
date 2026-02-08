package co.Example.Study.Center.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;
    private String paymentMode; // UPI, CARD, CASH
    private String status;      // SUCCESS, FAILED, PENDING
    private LocalDateTime paymentDate = LocalDateTime.now();

    // ✅ Student Relation (Many Payments → One Student)
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // ✅ Batch Relation (Many Payments → One Batch)
    @ManyToOne
    @JoinColumn(name = "batch_id")
    private BatchInfo batchInfo;



}
