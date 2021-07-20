package persistence.repositories.interfaces;


import persistence.entites.AssignmentResult;

public interface AssignmentResultRepository extends LoggedRepository<AssignmentResult> {

    AssignmentResult findById(Long id);
}
