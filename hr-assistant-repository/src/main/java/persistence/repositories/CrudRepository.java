package persistence.repositories;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.repositories.interfaces.LoggedRepository;
import persistence.session.HibernateSessionFactory;


public class CrudRepository<T> implements LoggedRepository<T> {
    private final Logger log = LoggerFactory.getLogger(CrudRepository.class);

    public void save(T t) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(t);
        transaction.commit();
        session.close();

        if (log.isDebugEnabled()) {
            log.debug(SQL_SAVE_STATEMENT + t.toString());
        }
    }

    public void update(T t) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(t);
        transaction.commit();
        session.close();

        if (log.isDebugEnabled()) {
            log.debug(SQL_UPDATE_STATEMENT + t.toString());
        }
    }

    @Override
    public void merge(T t) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(t);
        transaction.commit();
        session.close();

        if (log.isDebugEnabled()) {
            log.debug(SQL_UPDATE_STATEMENT + t.toString());
        }
    }

    public void delete(T t) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(t);
        transaction.commit();
        session.close();

        if (log.isDebugEnabled()) {
            log.debug(SQL_DELETE_STATEMENT + t.toString());
        }
    }
}
