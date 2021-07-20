package persistence.session;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entites.*;


/**
 * Класс для получения фабрики соединений с базой данных
 */
public class HibernateSessionFactory {
	private static final Logger log = LoggerFactory.getLogger(HibernateSessionFactory.class);
	private static final String CONFIG_FILE = "hibernate.cfg.xml";

	private static volatile SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			synchronized (HibernateSessionFactory.class) {
				try {
					Configuration configuration = new Configuration()
							.configure(CONFIG_FILE)
							.addAnnotatedClass(Candidate.class)
							.addAnnotatedClass(Vacancy.class)
							.addAnnotatedClass(Assignment.class)
							.addAnnotatedClass(AssignmentResult.class)
							.addAnnotatedClass(CompanyInfo.class);

					StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
					sessionFactory = configuration.buildSessionFactory(builder.build());
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
		return sessionFactory;
	}

	public static Session getSession() {
		Session session;
		try {
			session = getSessionFactory().getCurrentSession();
		} catch (HibernateException ex) {
			session = getSessionFactory().openSession();
		}
		return session;
	}
}
