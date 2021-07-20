package persistence.repositories;


import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entites.CompanyInfo;
import persistence.entites.Vacancy;
import persistence.repositories.interfaces.CompanyInfoRepository;
import persistence.session.HibernateSessionFactory;

import java.util.List;


public class CompanyInfoRepositoryImpl extends CrudRepository<CompanyInfo> implements CompanyInfoRepository {
    private final Logger log = LoggerFactory.getLogger(CompanyInfoRepositoryImpl.class);


    @Override
    public CompanyInfo findById(Long id) {
        Session session =  HibernateSessionFactory.getSession();
        CompanyInfo companyInfo = session.get(CompanyInfo.class, id);
        session.close();
        return companyInfo;
    }

    @Override
    public CompanyInfo findOneByActive() {
        final String sql = "from CompanyInfo where active = true";

        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery(sql);

        if (log.isDebugEnabled()) {
            log.debug(SQL_STATEMENT + query.getQueryString());
        }
        List<CompanyInfo> companyInfoList = query.getResultList();
        if (CollectionUtils.isEmpty(companyInfoList)) {
            log.error("Не найдено ни одной актуальной записи о компании");
            return null;
        }
        return companyInfoList.get(0);
    }
}
