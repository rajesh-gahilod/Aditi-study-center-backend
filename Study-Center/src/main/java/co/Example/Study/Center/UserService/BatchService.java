package co.Example.Study.Center.UserService;

import co.Example.Study.Center.dto.BatchInfoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BatchService {
    ResponseEntity<?> BatchStore(BatchInfoDTO batchInfoDTO);
    public ResponseEntity<?> BatchUpdate(BatchInfoDTO batchInfoDTO);
    ResponseEntity<?> getAllBatches();
    public long getBatchCount();

    int getBatchFee(String batchCode);
    public List<BatchInfoDTO> getAllBatchSchedules();
}
