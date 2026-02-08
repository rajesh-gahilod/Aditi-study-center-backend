package co.Example.Study.Center.UserRepo;

import co.Example.Study.Center.Entity.BatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BatchRepo extends JpaRepository<BatchInfo, Long> {
    Optional<BatchInfo> findByBatchCode(String batchCode);
//    Optional<BatchInfo> findByFee(Integer fee);
//    Optional<BatchInfo>findByTiming(String timing);


}
