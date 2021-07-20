package persistence.mappers;


import persistence.dto.AssignmentResultDto;
import persistence.dto.VacancyDto;
import persistence.entites.AssignmentResult;
import persistence.entites.Vacancy;
import persistence.mappers.interfaces.Mapper;
import persistence.repositories.CandidateRepositoryImpl;
import persistence.repositories.VacancyRepositoryImpl;
import persistence.repositories.interfaces.CandidateRepository;
import persistence.repositories.interfaces.VacancyRepository;


public class AssignmentResultMapper implements Mapper<AssignmentResult, AssignmentResultDto> {
    private final Mapper<Vacancy, VacancyDto> vacancyMapper;
    private final VacancyRepository vacancyRepository;
    private final CandidateRepository candidateRepository;

    public AssignmentResultMapper() {
        this.vacancyRepository = new VacancyRepositoryImpl();
        this.vacancyMapper = new VacancyMapper();
        this.candidateRepository = new CandidateRepositoryImpl();
    }

    @Override
    public AssignmentResultDto mapToDto(AssignmentResult assignmentResult) {
        if (assignmentResult == null)
            return null;

        return AssignmentResultDto.builder()
                .id(assignmentResult.getId())
                .fileName(assignmentResult.getFileName())
                .candidateId(assignmentResult.getCandidate().getId())
                .vacancyDto(vacancyMapper.mapToDto(assignmentResult.getVacancy()))
        .build();
    }

    @Override
    public AssignmentResult mapToEntity(AssignmentResultDto assignmentResultDto) {
        return AssignmentResult.builder()
                .fileName(assignmentResultDto.getFileName())
                .candidate(candidateRepository.findById(assignmentResultDto.getCandidateId()))
                .vacancy(vacancyRepository.findById(assignmentResultDto.getVacancyDto().getId()))
        .build();
    }
}
