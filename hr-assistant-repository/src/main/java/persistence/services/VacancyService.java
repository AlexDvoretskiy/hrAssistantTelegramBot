package persistence.services;


import com.google.common.cache.CacheBuilder;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistence.dto.VacancyDto;
import persistence.entites.Vacancy;
import persistence.mappers.VacancyMapper;
import persistence.mappers.interfaces.Mapper;
import persistence.repositories.VacancyRepositoryImpl;
import persistence.repositories.interfaces.VacancyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


public class VacancyService {
    private static final Logger log = LoggerFactory.getLogger(VacancyService.class);

    private static volatile VacancyService instance;
    private static LoadingCache<String, VacancyDto> loadingCache;

    private final VacancyRepository vacancyRepository;
    private final Mapper<Vacancy, VacancyDto> vacancyMapper;


    private VacancyService() {
        vacancyRepository = new VacancyRepositoryImpl();
        vacancyMapper = new VacancyMapper();
    }

    public static VacancyService getInstance() {
        VacancyService localInstance = instance;
        if (localInstance == null) {
            synchronized (VacancyService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new VacancyService();
                }
            }
        }
        return localInstance;
    }

    public VacancyDto findById(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id);
        return vacancyMapper.mapToDto(vacancy);
    }

    public VacancyDto findOneByTitleAndActiveTrue(String title) {
        if (loadingCache == null)
            loadingCache = buildCacheLoader();

        return loadingCache.getUnchecked(title);
    }

    public List<VacancyDto> findAllByActiveTrue() {
        if (loadingCache == null) {
            loadingCache = buildCacheLoader();
            loadAllByActiveTrueFromDb();
        }
        return new ArrayList<>(loadingCache.asMap().values());
    }

    public void setNotActive(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id);
        if (vacancy != null) {
            vacancy.setActive(false);
            vacancyRepository.update(vacancy);
            loadingCache.invalidate(vacancy.getTitle());
        }
    }

    public void save(VacancyDto vacancyDto) {
        vacancyRepository.save(vacancyMapper.mapToEntity(vacancyDto));
        loadingCache.refresh(vacancyDto.getTitle());
    }

    public void update(VacancyDto vacancyDto) {
        vacancyRepository.update(vacancyMapper.mapToEntity(vacancyDto));
        loadingCache.refresh(vacancyDto.getTitle());
    }

    private VacancyDto loadOneByTitleAndActiveTrue(String title) {
        Vacancy vacancy = vacancyRepository.findOneByTitleAndActiveTrue(title);
        return vacancyMapper.mapToDto(vacancy);
    }

    private void loadAllByActiveTrueFromDb() {
        List<Vacancy> vacancyList = vacancyRepository.findAllByActiveTrue();
        List<VacancyDto> vacancyDtoList = vacancyList.stream().map(vacancyMapper::mapToDto).collect(Collectors.toList());

        if (log.isDebugEnabled()) {
            log.debug("Cached objects ({}): {}", vacancyDtoList.size(), vacancyDtoList.toString());
        }
        loadingCache.putAll(vacancyDtoList.stream().collect(Collectors.toMap(VacancyDto::getTitle, vacancyDto -> vacancyDto)));
    }


    private LoadingCache<String, VacancyDto> buildCacheLoader () {
        return CacheBuilder.newBuilder()
                .build(new CacheLoader<String, VacancyDto>() {

                    @Override
                    public VacancyDto load(final String key) throws Exception {
                        VacancyDto vacancyDto = loadOneByTitleAndActiveTrue(key);
                        if (log.isDebugEnabled()) {
                            log.debug("Cached objects ({}): {}", 1, vacancyDto.toString());
                        }
                        return vacancyDto;
                    }
                });
    }
}
