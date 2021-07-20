package persistence.repositories;



import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entites.Assignment;
import persistence.entites.Vacancy;
import persistence.repositories.interfaces.AssignmentRepository;
import persistence.session.HibernateSessionFactory;

import java.util.List;


public class AssignmentRepositoryImpl extends CrudRepository<Assignment> implements AssignmentRepository {
    private final Logger log = LoggerFactory.getLogger(AssignmentRepositoryImpl.class);

    @Override
    public Assignment findById(Long id) {
        Session session =  HibernateSessionFactory.getSession();
        Assignment assignment = session.get(Assignment.class, id);
        session.close();
        return assignment;
    }

    @Override
    public List<Assignment> findAllByActiveTrue() {
        final String sql = "from Assignment where active = true order by id";

        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(sql);

        if (log.isDebugEnabled()) {
            log.debug(SQL_STATEMENT + query.getQueryString());
        }
        List<Assignment> vacancyList = query.getResultList();
        transaction.commit();
        return vacancyList;
    }

    @Override
    public Assignment findByFileNameAndActiveTrue(String fileName) {
        final String sql = "from Assignment where fileName = :fileName and active = true";

        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery(sql);
        query.setParameter("fileName", fileName);

        if (log.isDebugEnabled()) {
            log.debug(SQL_STATEMENT + query.getQueryString());
        }

        List<Assignment> assignmentList = query.getResultList();
        if (CollectionUtils.isEmpty(assignmentList)) {
            log.warn("Empty result query: {}", query.getQueryString());
            return null;
        }
        return assignmentList.get(0);
    }
}
