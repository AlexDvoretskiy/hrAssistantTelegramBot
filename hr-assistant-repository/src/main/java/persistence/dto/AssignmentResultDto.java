package persistence.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


@Data
@NoArgsConstructor
public class AssignmentResultDto {
    private Long id;
    private Long candidateId;
    private VacancyDto vacancyDto;
    private String fileName;

    @Builder
    public AssignmentResultDto(Long id, Long candidateId, VacancyDto vacancyDto, String fileName) {
        this.id = id;
        this.candidateId = candidateId;
        this.vacancyDto = vacancyDto;
        this.fileName = fileName;
    }

    public AssignmentResultDto(Long candidateId, VacancyDto vacancyDto, String fileName) {
        this.candidateId = candidateId;
        this.vacancyDto = vacancyDto;
        this.fileName = fileName;
    }
}
