package persistence.services;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dto.AssignmentDto;
import persistence.entites.Assignment;
import persistence.mappers.AssignmentMapper;
import persistence.mappers.interfaces.Mapper;
import persistence.repositories.AssignmentRepositoryImpl;
import persistence.repositories.interfaces.AssignmentRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class AssignmentService {
    private static final Logger log = LoggerFactory.getLogger(AssignmentService.class);

    private static volatile AssignmentService instance;

    private final AssignmentRepository assignmentRepository;
    private final Mapper<Assignment, AssignmentDto> assignmentMapper;


    private AssignmentService() {
        assignmentRepository = new AssignmentRepositoryImpl();
        assignmentMapper = new AssignmentMapper();
    }

    public static AssignmentService getInstance() {
        AssignmentService localInstance = instance;
        if (localInstance == null) {
            synchronized (AssignmentService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AssignmentService();
                }
            }
        }
        return localInstance;
    }

    public AssignmentDto findById(Long id) {
        Assignment assignment = assignmentRepository.findById(id);
        return assignmentMapper.mapToDto(assignment);
    }

    public List<AssignmentDto> findAllByActiveTrue() {
        List<Assignment> assignmentList = assignmentRepository.findAllByActiveTrue();

        if (CollectionUtils.isNotEmpty(assignmentList))
            return assignmentList.stream().map(assignmentMapper::mapToDto).collect(Collectors.toList());
        return Collections.emptyList();
    }

    public AssignmentDto save(AssignmentDto assignmentDto) {
        if (assignmentDto != null) {
            assignmentRepository.save(assignmentMapper.mapToEntity(assignmentDto));
            return assignmentMapper.mapToDto(assignmentRepository.findByFileNameAndActiveTrue(assignmentDto.getFileName()));
        }
        return null;
    }
}
