package persistence.repositories;



import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entites.Candidate;
import persistence.repositories.interfaces.CandidateRepository;
import persistence.session.HibernateSessionFactory;

import java.util.List;


public class CandidateRepositoryImpl extends CrudRepository<Candidate>  implements CandidateRepository {
    private final Logger log = LoggerFactory.getLogger(CandidateRepositoryImpl.class);

    @Override
    public List<Candidate> findAll() {
        final String sql = "from Candidate";

        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(sql);
        if (log.isDebugEnabled()) {
            log.debug(SQL_STATEMENT + query.getQueryString());
        }

        List<Candidate> candidateList = query.getResultList();
        if (CollectionUtils.isEmpty(candidateList)) {
            log.warn("Empty result query: {}", query.getQueryString());
            return null;
        }
        transaction.commit();
        return candidateList;
    }

    @Override
    public Candidate findById(Long id) {
        return HibernateSessionFactory.getSession().get(Candidate.class, id);
    }

    @Override
    public Candidate findByChatId(Long chatId) {
        final String sql = "from Candidate where chatId = :chatId";

        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(sql);
        query.setParameter("chatId", chatId);

        if (log.isDebugEnabled()) {
            log.debug(SQL_STATEMENT + query.getQueryString());
        }

        List<Candidate> candidateList = query.getResultList();
        if (CollectionUtils.isEmpty(candidateList)) {
            log.warn("Empty result query: {}", query.getQueryString());
            return null;
        }
        transaction.commit();
        return candidateList.get(0);
    }
}
