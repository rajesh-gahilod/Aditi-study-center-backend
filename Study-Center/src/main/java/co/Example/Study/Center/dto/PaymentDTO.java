package co.Example.Study.Center.dto;

import lombok.*;


@Data
public class PaymentDTO {

    private Long studentId;
    private String batchCode;   // ✅ सिर्फ batchCode से batch identify होगा
    private String paymentMode;


}