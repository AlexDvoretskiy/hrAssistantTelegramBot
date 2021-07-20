package persistence.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.dto.CompanyInfoDto;
import persistence.entites.CompanyInfo;
import persistence.mappers.CompanyInfoMapper;
import persistence.mappers.interfaces.Mapper;
import persistence.repositories.CompanyInfoRepositoryImpl;
import persistence.repositories.interfaces.CompanyInfoRepository;


public class CompanyInfoService {
    private static final Logger log = LoggerFactory.getLogger(CompanyInfoService.class);
    private static volatile CompanyInfoService instance;

    private final CompanyInfoRepository companyInfoRepository;
    private final Mapper<CompanyInfo, CompanyInfoDto> companyInfoMapper;

    private CompanyInfoService() {
        companyInfoRepository = new CompanyInfoRepositoryImpl();
        companyInfoMapper = new CompanyInfoMapper();
    }

    public static CompanyInfoService getInstance() {
        CompanyInfoService localInstance = instance;
        if (localInstance == null) {
            synchronized (CompanyInfoService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CompanyInfoService();
                }
            }
        }
        return localInstance;
    }

    public void save(CompanyInfoDto companyInfoDto) {
        companyInfoRepository.save(companyInfoMapper.mapToEntity(companyInfoDto));
    }

    public void update(CompanyInfoDto companyInfoDto) {
        companyInfoRepository.update(companyInfoMapper.mapToEntity(companyInfoDto));
    }

    public CompanyInfoDto findOneByActiveTrue() {
        CompanyInfo companyInfo = companyInfoRepository.findOneByActive();
        return companyInfoMapper.mapToDto(companyInfo);
    }

    private void updateCompanyInfo(CompanyInfo companyInfo, CompanyInfoDto companyInfoDto) {
        companyInfo.setName(companyInfoDto.getName());
        companyInfo.setDescription(companyInfoDto.getDescription());
        companyInfo.setContacts(companyInfoDto.getContacts());
    }
}
