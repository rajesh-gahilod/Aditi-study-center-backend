    package co.Example.Study.Center.UserController;

    import co.Example.Study.Center.UserService.BatchService;
    import co.Example.Study.Center.UserServiceImpl.BatchInfoServiceImpl;
    import co.Example.Study.Center.dto.BatchInfoDTO;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/batchApi")
    @CrossOrigin(
            origins = {"http://127.0.0.1:5500", "http://localhost:5500"}
    )
    public class BatchInfoController {
        @Autowired
        private BatchService batchService;

        @PostMapping("/createBatch")
        public ResponseEntity<?> createBatch(@RequestBody BatchInfoDTO batchInfoDTO) {
            return batchService.BatchStore(batchInfoDTO);
        }

        // ✅ Update Batch
        @PutMapping("/updateBatch")
        public ResponseEntity<?> updateBatch(@RequestBody BatchInfoDTO batchInfoDTO) {
             batchService.BatchUpdate(batchInfoDTO);
             return new ResponseEntity<>("Update " + batchInfoDTO.getBatchCode(), HttpStatus.OK);
        }

        // ✅ Get All Batches
//        @GetMapping("/all")
//        public ResponseEntity<?> getAllBatches() {
//            return batchService.getAllBatches();
//        }
        @GetMapping("/batches/schedule")
        public List<BatchInfoDTO> getBatchSchedule() {
            return batchService.getAllBatchSchedules();
        }


        @GetMapping("/getAllBatches")
        public ResponseEntity<?> getAllBatches() {
            return batchService.getAllBatches();
        }

        @GetMapping("/Batchcount")
        public ResponseEntity<Long> getStudentCount() {
            long count = batchService.getBatchCount();
            return ResponseEntity.ok(count);
        }

        // 🔹 GET BATCH FEE (🔥 frontend ke liye)
        @GetMapping("/{batchCode}/fee")
        public ResponseEntity<Integer> getBatchFee(
                @PathVariable String batchCode) {

            int fee = batchService.getBatchFee(batchCode);
            return ResponseEntity.ok(fee);
        }
    }
