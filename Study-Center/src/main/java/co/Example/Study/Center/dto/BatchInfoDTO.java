package co.Example.Study.Center.dto;

import co.Example.Study.Center.Entity.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchInfoDTO {
    private String batchCode;

    private Integer fee;
    private String timing;

    // 🟢 One Batch → Many Students
//    @OneToMany(mappedBy = "batchInfo", cascade = CascadeType.ALL)
//    private List<Student> students;
}

