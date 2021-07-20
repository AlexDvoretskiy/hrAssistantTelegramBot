package persistence.mappers;


import persistence.dto.AssignmentDto;
import persistence.dto.VacancyDto;
import persistence.entites.Assignment;
import persistence.entites.Vacancy;
import persistence.mappers.interfaces.Mapper;
import persistence.repositories.AssignmentRepositoryImpl;
import persistence.repositories.interfaces.AssignmentRepository;

public class VacancyMapper implements Mapper<Vacancy, VacancyDto> {
    private final Mapper<Assignment, AssignmentDto> assignmentMapper;
    private final AssignmentRepository assignmentRepository;

    public VacancyMapper() {
        assignmentMapper = new AssignmentMapper();
        assignmentRepository = new AssignmentRepositoryImpl();
    }

    @Override
    public VacancyDto mapToDto(Vacancy vacancy) {
        return VacancyDto.builder()
                .id(vacancy.getId())
                .title(vacancy.getTitle())
                .description(vacancy.getDescription())
                .requirements(vacancy.getRequirements())
                .conditions(vacancy.getConditions())
                .salary(vacancy.getSalary())
                .assignmentDto(assignmentMapper.mapToDto(vacancy.getAssignment()))
                .active(vacancy.isActive())
        .build();
    }

    @Override
    public Vacancy mapToEntity(VacancyDto vacancyDto) {
        Vacancy vacancy = Vacancy.builder()
                .id(vacancyDto.getId())
                .title(vacancyDto.getTitle())
                .description(vacancyDto.getDescription())
                .requirements(vacancyDto.getRequirements())
                .conditions(vacancyDto.getConditions())
                .salary(vacancyDto.getSalary())
        .build();

        if (vacancyDto.hasAssignment())
            vacancy.setAssignment(assignmentRepository.findById(vacancyDto.getAssignmentDto().getId()));

        return vacancy;
    }
}
