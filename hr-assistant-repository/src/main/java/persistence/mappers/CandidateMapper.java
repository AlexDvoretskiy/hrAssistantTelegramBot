package persistence.mappers;


import org.apache.commons.collections4.CollectionUtils;
import persistence.dto.AssignmentResultDto;
import persistence.dto.CandidateDto;
import persistence.dto.VacancyDto;
import persistence.entites.AssignmentResult;
import persistence.entites.Candidate;
import persistence.entites.Vacancy;
import persistence.mappers.interfaces.Mapper;
import persistence.repositories.AssignmentResultRepositoryImpl;
import persistence.repositories.VacancyRepositoryImpl;
import persistence.repositories.interfaces.AssignmentResultRepository;
import persistence.repositories.interfaces.VacancyRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class CandidateMapper implements Mapper<Candidate, CandidateDto> {
    private final Mapper<Vacancy, VacancyDto> vacancyMapper;
    private final Mapper<AssignmentResult, AssignmentResultDto> assignmentResultMapper;
    private final VacancyRepository vacancyRepository;
    private final AssignmentResultRepository assignmentResultRepository;

    public CandidateMapper() {
        this.vacancyRepository = new VacancyRepositoryImpl();
        this.assignmentResultMapper = new AssignmentResultMapper();
        this.vacancyMapper = new VacancyMapper();
        this.assignmentResultRepository = new AssignmentResultRepositoryImpl();
    }

    @Override
    public CandidateDto mapToDto(Candidate candidate) {
        CandidateDto candidateDto =  CandidateDto.builder()
                .id(candidate.getId())
                .chatId(candidate.getChatId())
                .stage(candidate.getStage())
                .firstName(candidate.getFirstName())
                .middleName(candidate.getMiddleName())
                .lastName(candidate.getLastName())
                .dateOfBirth(candidate.getDateOfBirth())
                .phone(candidate.getPhone())
                .email(candidate.getEmail())
                .cvFileName(candidate.getCvFileName())
                .applyDate(candidate.getApplyDate())
        .build();

        if (CollectionUtils.isNotEmpty(candidate.getVacancyList())) {
            List<VacancyDto> vacancyDtoList = candidate.getVacancyList().stream()
                    .map(vacancyMapper::mapToDto)
                    .collect(Collectors.toList());
            candidateDto.setVacancyList(vacancyDtoList);
        }

        if (CollectionUtils.isNotEmpty(candidate.getAssignmentResults())) {
            List<AssignmentResultDto> assignmentResultList = candidate.getAssignmentResults().stream()
                    .map(assignmentResultMapper::mapToDto)
                    .collect(Collectors.toList());
            candidateDto.setAssignmentResultDtoList(assignmentResultList);
        }

        return candidateDto;
    }

    @Override
    public Candidate mapToEntity(CandidateDto candidateDto) {
        Candidate candidate =  Candidate.builder()
                .id(candidateDto.getId())
                .chatId(candidateDto.getChatId())
                .stage(candidateDto.getStage())
                .firstName(candidateDto.getFirstName())
                .middleName(candidateDto.getMiddleName())
                .lastName(candidateDto.getLastName())
                .dateOfBirth(candidateDto.getDateOfBirth())
                .phone(candidateDto.getPhone())
                .email(candidateDto.getEmail())
                .cvFileName(candidateDto.getCvFileName())
        .build();

        List<VacancyDto> vacancyDtoList = candidateDto.getVacancyList();
        if (CollectionUtils.isNotEmpty(vacancyDtoList)) {
            List<Vacancy> vacancyList = new LinkedList<>();

            for (VacancyDto vacancyDto : vacancyDtoList) {
                if (vacancyDto != null) {
                    vacancyList.add(vacancyRepository.findById(vacancyDto.getId()));
                }
            }
            candidate.setVacancyList(vacancyList);
        }

        List<AssignmentResultDto> assignmentResultDtoList = candidateDto.getAssignmentResultDtoList();
        if (CollectionUtils.isNotEmpty(assignmentResultDtoList)) {
            List<AssignmentResult> assignmentResults = new LinkedList<>();

            for (AssignmentResultDto assignmentResultDto : assignmentResultDtoList) {
                AssignmentResult assignmentResult = null;

                if (assignmentResultDto.getId() != null)
                    assignmentResult = assignmentResultRepository.findById(assignmentResultDto.getId());

                if (assignmentResult == null)
                    assignmentResult = new AssignmentResult(candidate,
                            vacancyRepository.findById(assignmentResultDto.getVacancyDto().getId()),
                            assignmentResultDto.getFileName());
                assignmentResults.add(assignmentResult);
            }
            candidate.setAssignmentResults(assignmentResults);
        }

        return candidate;
    }
}
