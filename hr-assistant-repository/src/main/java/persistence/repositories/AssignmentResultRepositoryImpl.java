package persistence.repositories;



import org.hibernate.Session;
import persistence.entites.AssignmentResult;
import persistence.repositories.interfaces.AssignmentResultRepository;
import persistence.session.HibernateSessionFactory;


public class AssignmentResultRepositoryImpl extends CrudRepository<AssignmentResult> implements AssignmentResultRepository {

    @Override
    public AssignmentResult findById(Long id) {
        Session session =  HibernateSessionFactory.getSession();
        AssignmentResult assignmentResult = session.get(AssignmentResult.class, id);
        session.close();
        return assignmentResult;
    }
}
