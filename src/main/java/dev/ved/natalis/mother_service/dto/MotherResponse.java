package dev.ved.natalis.mother_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MotherResponse {
    private String id;
    private String userId;
    private String fullName;
    private LocalDate dateOfBirth;
    private Integer pregnancyWeek;
    private LocalDate expectedDueDate;
    private String bloodGroup;
    private String emergencyContact;
}