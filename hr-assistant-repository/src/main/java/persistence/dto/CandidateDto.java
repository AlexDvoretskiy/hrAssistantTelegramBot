package persistence.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import persistence.enums.Stage;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Data
@NoArgsConstructor
public class CandidateDto {
    private Long id;
    private Long chatId;
    private Stage stage;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private String email;
    private String phone;
    private Date applyDate;
    private String cvFileName;
    private VacancyDto currentVacancy;
    private List<VacancyDto> vacancyList;
    private List<AssignmentResultDto> assignmentResultDtoList;

    public CandidateDto(Long chatId, Stage stage) {
        this.chatId = chatId;
        this.stage = stage;
    }

    @Builder
    public CandidateDto(Long id, Long chatId, Stage stage, String firstName, String middleName, String lastName, Date dateOfBirth, String email,
                        String phone, String cvFileName, Date applyDate, List<AssignmentResultDto> assignmentResultDtoList, List<VacancyDto> vacancyList) {
        this.id = id;
        this.chatId = chatId;
        this.stage = stage;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.cvFileName = cvFileName;
        this.applyDate = applyDate;
        this.assignmentResultDtoList = assignmentResultDtoList;
        this.vacancyList = vacancyList;
    }

    public void addCurrentVacancyToVacancyList() {
        if (CollectionUtils.isEmpty(vacancyList)) {
            vacancyList = new LinkedList<>();
            vacancyList.add(currentVacancy);
        } else {
            if (!vacancyList.contains(currentVacancy)) {
                vacancyList.add(currentVacancy);
            }
        }
    }

    public void addAssignmentResult(AssignmentResultDto assignmentResultDto) {
        if (CollectionUtils.isEmpty(assignmentResultDtoList)) {
            assignmentResultDtoList = new LinkedList<>();
        }
        assignmentResultDtoList.add(assignmentResultDto);
    }

    public AssignmentResultDto getAssignmentByVacancyId(Long vacancyId) {
        if (CollectionUtils.isNotEmpty(assignmentResultDtoList)) {
            Optional<AssignmentResultDto> optional = assignmentResultDtoList.stream()
                    .filter(a -> a.getVacancyDto().getId().equals(vacancyId))
                    .findFirst();

            if (optional.isPresent())
                return optional.get();
        }
        return null;
    }

    public static CandidateDto createDefault(Long chatId) {
        return new CandidateDto(chatId, Stage.REGISTERED);
    }
}
