package com.hrAssistantWeb.services;


import com.hrAssistantWeb.model.CandidateModel;
import com.hrAssistantWeb.utils.DateTimeUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import persistence.dto.AssignmentResultDto;
import persistence.dto.CandidateDto;
import persistence.dto.VacancyDto;
import persistence.services.CandidateService;

import java.util.LinkedList;
import java.util.List;


@Service
public class CandidateModelService {
    private final CandidateService candidateService;

    public CandidateModelService() {
        this.candidateService = CandidateService.getInstance();
    }

    public List<CandidateModel> getCandidateModelList() {
        List<CandidateModel> candidateModels = new LinkedList<>();
        List<CandidateDto> candidateDtoList = candidateService.findAll();

        for (CandidateDto candidateDto : candidateDtoList) {
            if (CollectionUtils.isEmpty(candidateDto.getVacancyList())) {
                candidateModels.add(mapToCandidateModel(candidateDto, StringUtils.EMPTY, StringUtils.EMPTY));
            } else {
                for (VacancyDto vacancyDto : candidateDto.getVacancyList()) {
                    AssignmentResultDto assignmentResultDto = candidateDto.getAssignmentByVacancyId(vacancyDto.getId());
                    String assignmentFileName = assignmentResultDto != null ?
                            assignmentResultDto.getFileName() : StringUtils.EMPTY;

                    candidateModels.add(mapToCandidateModel(candidateDto, vacancyDto.getTitle(), assignmentFileName));
                }
            }
        }
        return candidateModels;
    }

    private CandidateModel mapToCandidateModel(CandidateDto candidateDto, String vacancyName, String assignmentFileName) {
        return CandidateModel.builder()
                .id(candidateDto.getId())
                .stage(candidateDto.getStage().getDescription())
                .firstName(candidateDto.getFirstName())
                .middleName(candidateDto.getMiddleName())
                .lastName(candidateDto.getLastName())
                .dateOfBirth(DateTimeUtils.convertDateToString(candidateDto.getDateOfBirth()))
                .email(candidateDto.getEmail())
                .phone(candidateDto.getPhone())
                .applyDate(DateTimeUtils.convertDateToString(candidateDto.getApplyDate()))
                .vacancyName(StringUtils.isEmpty(vacancyName) ? "Не выбрана" : vacancyName)
                .cvFileName(candidateDto.getCvFileName())
                .assignmentFileName(assignmentFileName)
        .build();
    }
}
