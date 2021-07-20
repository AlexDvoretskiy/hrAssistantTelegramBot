package persistence.repositories.interfaces;


import persistence.entites.CompanyInfo;

public interface CompanyInfoRepository extends LoggedRepository<CompanyInfo> {

    CompanyInfo findById(Long id);

    CompanyInfo findOneByActive();
}
