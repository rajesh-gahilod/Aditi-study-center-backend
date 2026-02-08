package co.Example.Study.Center.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // Auto Increment Primary Key
    private Long id;
    @Column(unique = true, nullable = false)
    private String batchCode;
    private Integer fee;
    private String timing;

    // 🟢 One Batch → Many Students
    @OneToMany(mappedBy = "batchInfo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Student> students;






}




