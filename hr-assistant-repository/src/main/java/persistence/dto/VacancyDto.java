package persistence.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class VacancyDto {
    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String conditions;
    private BigDecimal salary;
    private boolean active;
    private AssignmentDto assignmentDto;


    @Builder
    public VacancyDto(Long id, String title, String description, String requirements, String conditions, BigDecimal salary, AssignmentDto assignmentDto, boolean active) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.conditions = conditions;
        this.salary = salary;
        this.assignmentDto = assignmentDto;
        this.active = active;
    }

    public boolean hasAssignment() {
        return assignmentDto != null;
    }

    public String getHtmlDescription() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<strong>").append(title).append("</strong> \n\n")
                .append("<i> Описание вакансии: </i> \n").append(description).append("\n\n")
                .append("<i> Требования: </i> \n").append(requirements).append("\n\n");

        if (StringUtils.isNotEmpty(conditions))
            stringBuilder.append("<i> Условия работы: </i> \n").append(conditions).append("\n\n");

        if (salary != null && !salary.equals(BigDecimal.ZERO)) {
            stringBuilder.append("<i> Заработная плата от: </i> \n").append(salary).append(" руб.");
        } else {
            stringBuilder.append("<i> Заработная плата: </i> \n").append("по договоренности");
        }

        return stringBuilder.toString();
    }
}
