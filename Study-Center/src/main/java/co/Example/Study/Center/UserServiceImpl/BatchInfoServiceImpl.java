package co.Example.Study.Center.UserServiceImpl;

import co.Example.Study.Center.Entity.BatchInfo;
import co.Example.Study.Center.UserRepo.BatchRepo;
import co.Example.Study.Center.UserService.BatchService;
import co.Example.Study.Center.dto.BatchInfoDTO;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchInfoServiceImpl implements BatchService {

    @Autowired
    private BatchRepo batchRepo;

    public BatchInfo createBatchInfo(String batchCode) {
        BatchInfo batchInfo = new BatchInfo();

        switch (batchCode) {
            case "A1":
                batchInfo.setBatchCode("A1");
                batchInfo.setFee(650);
                batchInfo.setTiming("Monday - Saturday 08:00 AM - 02:00 PM !! Sunday 08:00 AM - 01:00 PM");
                break;

            case "A2":
                batchInfo.setBatchCode("A2");
                batchInfo.setFee(650);
                batchInfo.setTiming("Monday - Saturday 02:00 PM - 08:00 PM !! Sunday 01:00 PM - 08:00 PM");
                break;

            case "A3":
                batchInfo.setBatchCode("A3");
                batchInfo.setFee(1400);
                batchInfo.setTiming("Monday - Saturday 08:00 AM - 10:00 PM !! Sunday 08:00 AM - 08:00 PM");
                break;

            case "A4":
                batchInfo.setBatchCode("A4");
                batchInfo.setFee(500);
                batchInfo.setTiming("Monday - Saturday 08:00 AM - 12:00 PM || 12:00 PM - 04:00 PM || 04:00 PM - 08:00 PM !! Sunday 01:00 AM - 08:00 PM");
                break;

            default:
                throw new RuntimeException("Invalid batch selected! Choose A1, A2, A3, or A4.");
        }

        return batchInfo;
    }

    @Override
    public ResponseEntity<?> BatchStore(BatchInfoDTO batchInfoDTO) {

        // 🔹 1. Duplicate check
        if (batchRepo.findByBatchCode(batchInfoDTO.getBatchCode()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Batch already exists with code: " + batchInfoDTO.getBatchCode());
        }

        // 🔹 2. Create batch from switch-case
        BatchInfo batchInfo = createBatchInfo(batchInfoDTO.getBatchCode());

        // 🔹 3. Save
        BatchInfo savedBatch = batchRepo.save(batchInfo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedBatch);
    }


    @Override
    public ResponseEntity<?> BatchUpdate(BatchInfoDTO batchInfoDTO) {
        BatchInfo existingBatch = batchRepo.findByBatchCode(batchInfoDTO.getBatchCode())
                .orElseThrow(() -> new RuntimeException("Batch with code " + batchInfoDTO.getBatchCode() + " not found!"));

        if (batchInfoDTO.getFee() != null) {
            existingBatch.setFee(batchInfoDTO.getFee());
        }
        if (batchInfoDTO.getTiming() != null && !batchInfoDTO.getTiming().isEmpty()) {
            existingBatch.setTiming(batchInfoDTO.getTiming());
        }

        BatchInfo updatedBatch = batchRepo.save(existingBatch);

        return ResponseEntity.ok(updatedBatch);
    }


    @Override
    public List<BatchInfoDTO> getAllBatchSchedules() {
        return batchRepo.findAll()
                .stream()
                .map(batch -> {
                    BatchInfoDTO dto = new BatchInfoDTO();
                    dto.setBatchCode(batch.getBatchCode());
//                    dto.setTiming(batch.getDays());
                    dto.setTiming(batch.getTiming());
                    return dto;
                }).toList();
    }



    @Override
    public ResponseEntity<List<BatchInfo>> getAllBatches() {

        List<BatchInfo> batches = batchRepo.findAll();

        return ResponseEntity.ok(batches); // ✅ empty list bhi ok
    }

    @Override
    public long getBatchCount() {
        return batchRepo.count();
    }


    // 🔹 GET FEE (🔥 Razorpay ke liye important)
    @Override
    public int getBatchFee(String batchCode) {

        BatchInfo batchInfo = batchRepo.findByBatchCode(batchCode)
                .orElseThrow(() ->
                        new RuntimeException("Batch not found with code: " + batchCode));

        return batchInfo.getFee();
    }

}
