package co.Example.Study.Center.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SpeacileDays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Primary Key Auto Increment
    private Long id;
}
