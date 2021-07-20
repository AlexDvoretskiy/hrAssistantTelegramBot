package persistence.repositories;


import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entites.Vacancy;
import persistence.repositories.interfaces.VacancyRepository;
import persistence.session.HibernateSessionFactory;

import java.util.List;


public class VacancyRepositoryImpl extends CrudRepository<Vacancy> implements VacancyRepository {
    private final Logger log = LoggerFactory.getLogger(VacancyRepositoryImpl.class);


    @Override
    public Vacancy findById(Long id) {
        Session session =  HibernateSessionFactory.getSession();
        Vacancy vacancy = session.get(Vacancy.class, id);
        session.close();
        return vacancy;
    }

    @Override
    public Vacancy findOneByTitleAndActiveTrue(String title) {
        final String sql = "from Vacancy where title = :title and active = true";

        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery(sql);
        query.setParameter("title", title);

        if (log.isDebugEnabled()) {
            log.debug(SQL_STATEMENT + query.getQueryString());
        }

        List<Vacancy> vacancyList = query.getResultList();
        if (CollectionUtils.isEmpty(vacancyList)) {
            log.warn("Empty result query: {}", query.getQueryString());
            return null;
        }
        return vacancyList.get(0);
    }

    @Override
    public List<Vacancy> findAllByActiveTrue() {
        final String sql = "from Vacancy where active = true order by id";

        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(sql);

        if (log.isDebugEnabled()) {
            log.debug(SQL_STATEMENT + query.getQueryString());
        }
        List<Vacancy> vacancyList = query.getResultList();
        transaction.commit();
        return vacancyList;
    }
}
