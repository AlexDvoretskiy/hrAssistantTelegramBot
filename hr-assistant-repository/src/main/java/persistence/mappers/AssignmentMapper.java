package persistence.mappers;


import persistence.dto.AssignmentDto;
import persistence.entites.Assignment;
import persistence.mappers.interfaces.Mapper;


public class AssignmentMapper implements Mapper<Assignment, AssignmentDto> {

    @Override
    public AssignmentDto mapToDto(Assignment assignment) {
        if (assignment == null)
            return null;

        return AssignmentDto.builder()
                .id(assignment.getId())
                .fileName(assignment.getFileName())
                .description(assignment.getDescription())
                .active(assignment.isActive())
        .build();
    }

    @Override
    public Assignment mapToEntity(AssignmentDto assignmentDto) {
        return new Assignment(assignmentDto.getFileName(), assignmentDto.getDescription(), assignmentDto.isActive());
    }
}
