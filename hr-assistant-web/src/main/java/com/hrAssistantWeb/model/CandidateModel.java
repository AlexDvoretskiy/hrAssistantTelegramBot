package com.hrAssistantWeb.model;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CandidateModel {
    private Long id;
    private String stage;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phone;
    private String applyDate;
    private String vacancyName;
    private String cvFileName;
    private String assignmentFileName;
}
