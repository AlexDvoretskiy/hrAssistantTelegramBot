package com.hrAssistantWeb.services;


import com.hrAssistantWeb.model.VacancyModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import persistence.dto.AssignmentDto;
import persistence.dto.VacancyDto;
import persistence.services.AssignmentService;
import persistence.services.VacancyService;

import java.util.LinkedList;
import java.util.List;


@Slf4j
@Service
public class VacancyModelService {

    private final VacancyService vacancyService;
    private final AssignmentService assignmentService;
    private final FileService fileService;

    @Autowired
    public VacancyModelService(FileService fileService) {
        this.fileService = fileService;
        vacancyService = VacancyService.getInstance();
        assignmentService = AssignmentService.getInstance();
    }

    public List<VacancyModel> findAllByActiveTrue() {
        List<VacancyModel> vacancyModelList = new LinkedList<>();
        List<VacancyDto> vacancyDtoList = vacancyService.findAllByActiveTrue();

        for (VacancyDto vacancyDto : vacancyDtoList) {
            vacancyModelList.add(mapToVacancyModel(vacancyDto));
        }
        return vacancyModelList;
    }

    public VacancyModel findById(Long id) {
        VacancyDto vacancyDto = vacancyService.findById(id);
        if (vacancyDto != null) {
            return mapToVacancyModel(vacancyDto);
        }
        return null;
    }

    public List<AssignmentDto> findAllAssignments() {
        List<AssignmentDto> assignmentDtoList = assignmentService.findAllByActiveTrue();
        assignmentDtoList.add(new AssignmentDto("отсутсвует"));
        return assignmentDtoList;
    }

    public void save(VacancyModel vacancyModel, MultipartFile assignmentFile) {
        if (vacancyModel != null) {
            if (!assignmentFile.isEmpty()) {
                fileService.saveAssignmentFile(assignmentFile);
                AssignmentDto assignmentDto = assignmentService.save(new AssignmentDto(assignmentFile.getOriginalFilename(),
                        vacancyModel.getAssignmentDescription(), true));
                vacancyModel.setAssignmentFileId(assignmentDto.getId());
            }
            vacancyService.save(mapToVacancyDto(vacancyModel));
        } else {
            log.error("Ошибка при сохранении Вакансии: vacancyModel is null");
        }
    }

    public void update(VacancyModel vacancyModel) {
         if (vacancyModel != null) {
            vacancyService.update(mapToVacancyDto(vacancyModel));
         } else {
             log.error("Ошибка при обновлении Вакансии: vacancyModel is null");
         }
    }

    public void remove(Long id) {
        vacancyService.setNotActive(id);
    }

    private VacancyModel mapToVacancyModel(VacancyDto vacancyDto) {
        VacancyModel vacancyModel = VacancyModel.builder()
                .id(vacancyDto.getId())
                .title(vacancyDto.getTitle())
                .description(vacancyDto.getDescription())
                .requirements(vacancyDto.getRequirements())
                .conditions(vacancyDto.getConditions())
                .salary(vacancyDto.getSalary())
                .build();

        AssignmentDto assignmentDto = vacancyDto.getAssignmentDto();
        if (assignmentDto != null && StringUtils.isNotEmpty(assignmentDto.getFileName())) {
            vacancyModel.setAssignmentFileName(assignmentDto.getFileName());
        }
        return vacancyModel;
    }

    private VacancyDto mapToVacancyDto(VacancyModel vacancyModel) {
        VacancyDto vacancyDto = VacancyDto.builder()
                .id(vacancyModel.getId())
                .title(vacancyModel.getTitle())
                .description(vacancyModel.getDescription())
                .requirements(vacancyModel.getRequirements())
                .conditions(vacancyModel.getConditions())
                .salary(vacancyModel.getSalary())
                .active(true)
        .build();

        if (vacancyModel.getAssignmentFileId() != null) {
            vacancyDto.setAssignmentDto(assignmentService.findById(vacancyModel.getAssignmentFileId()));
        }
        return vacancyDto;
    }
}
