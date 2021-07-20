package persistence.mappers;

import persistence.dto.CompanyInfoDto;
import persistence.entites.CompanyInfo;
import persistence.mappers.interfaces.Mapper;


public class CompanyInfoMapper implements Mapper<CompanyInfo, CompanyInfoDto> {

    @Override
    public CompanyInfoDto mapToDto(CompanyInfo companyInfo) {
        return new CompanyInfoDto(companyInfo.getId(), companyInfo.getName(), companyInfo.getDescription(), companyInfo.getContacts());
    }

    @Override
    public CompanyInfo mapToEntity(CompanyInfoDto companyInfoDto) {
        return new CompanyInfo(companyInfoDto.getId(), companyInfoDto.getName(), companyInfoDto.getDescription(), companyInfoDto.getContacts());
    }
}
