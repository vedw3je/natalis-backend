package dev.ved.natalis.tests_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tests")
public class MedicalTest {
    @Id
    private String id;

    private String motherId;

    private String testName;
    private String description;
    private LocalDateTime appointmentDate;

    private String status;
    private String resultSummary;
    private String laboratoryName;
}
