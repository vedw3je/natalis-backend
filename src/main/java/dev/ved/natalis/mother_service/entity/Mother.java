package dev.ved.natalis.mother_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "mothers")
public class Mother {
    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private String fullName;
    private LocalDate dateOfBirth;
    private Integer pregnancyWeek;
    private LocalDate expectedDueDate;

    private String bloodGroup;
    private String emergencyContact;
}