package persistence.repositories.interfaces;


import persistence.entites.Assignment;

import java.util.List;


public interface AssignmentRepository extends LoggedRepository<Assignment> {

    Assignment findById(Long id);

    List<Assignment> findAllByActiveTrue();

    Assignment findByFileNameAndActiveTrue(String fileName);
}
