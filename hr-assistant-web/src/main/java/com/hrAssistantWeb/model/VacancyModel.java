package com.hrAssistantWeb.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.File;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class VacancyModel {
    private Long id;

    @NotNull(message = "Данное поле должно быть заполнено")
    @Size(min = 1, message = "Данное поле должно быть заполнено")
    private String title;

    @NotNull(message = "Данное поле должно быть заполнено")
    @Size(min = 1, message = "Данное поле должно быть заполнено")
    private String description;

    @NotNull(message = "Данное поле должно быть заполнено")
    @Size(min = 1, message = "Данное поле должно быть заполнено")
    private String requirements;

    @NotNull(message = "Данное поле должно быть заполнено")
    @Size(min = 1, message = "Данное поле должно быть заполнено")
    private String conditions;

    private BigDecimal salary;
    private Long assignmentFileId;
    private String assignmentFileName;
    private String assignmentDescription;

    @Builder
    public VacancyModel(Long id, String title, String description, String requirements, String conditions, BigDecimal salary, Long assignmentFileId, String assignmentFileName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.conditions = conditions;
        this.salary = salary;
        this.assignmentFileId = assignmentFileId;
        this.assignmentFileName = assignmentFileName;
    }
}
