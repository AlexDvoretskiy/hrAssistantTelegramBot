package persistence.services;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistence.dto.CandidateDto;
import persistence.entites.Candidate;
import persistence.mappers.CandidateMapper;
import persistence.mappers.interfaces.Mapper;
import persistence.repositories.CandidateRepositoryImpl;
import persistence.repositories.interfaces.CandidateRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class CandidateService {
    private static final Logger log = LoggerFactory.getLogger(CandidateService.class);

    private static volatile CandidateService instance;
    private static LoadingCache<Long, CandidateDto> loadingCache;

    private final CandidateRepository candidateRepository;
    private final Mapper<Candidate, CandidateDto> candidateMapper;


    private CandidateService() {
        candidateRepository = new CandidateRepositoryImpl();
        candidateMapper = new CandidateMapper();
        loadingCache = buildCacheLoader();
    }

    public static CandidateService getInstance() {
        CandidateService localInstance = instance;
        if (localInstance == null) {
            synchronized (CandidateService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CandidateService();
                }
            }
        }
        return localInstance;
    }

    public void createUserIfNotExists(Long chatId) {
        Candidate candidate = candidateRepository.findByChatId(chatId);
        if (candidate == null) {
            CandidateDto candidateDto = CandidateDto.createDefault(chatId);
            save(candidateDto);
            log.info("New user added: {}", candidateDto.toString());
        }
    }

    public List<CandidateDto> findAll() {
        List<Candidate> candidateList = candidateRepository.findAll();
        if (CollectionUtils.isNotEmpty(candidateList)) {
            return candidateList.stream()
                    .map(candidateMapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public CandidateDto findByChatId(Long chatId) {
        return loadingCache.getUnchecked(chatId);
    }

    public void save(CandidateDto candidateDto) {
        candidateRepository.save(candidateMapper.mapToEntity(candidateDto));
        loadingCache.refresh(candidateDto.getChatId());
    }

    public void update(CandidateDto candidateDto) {
        candidateDto.addCurrentVacancyToVacancyList();
        candidateRepository.update(candidateMapper.mapToEntity(candidateDto));
    }

    public void updateAndRefresh(CandidateDto candidateDto) {
        candidateDto.addCurrentVacancyToVacancyList();
        candidateRepository.merge(candidateMapper.mapToEntity(candidateDto));
        loadingCache.refresh(candidateDto.getChatId());
    }

    private CandidateDto loadOneByIdFromDb(Long chatId) {
        Candidate candidate = candidateRepository.findByChatId(chatId);
        return candidateMapper.mapToDto(candidate);
    }

    private LoadingCache<Long, CandidateDto> buildCacheLoader () {
        return CacheBuilder.newBuilder()
                .build(new CacheLoader<Long, CandidateDto>() {

                    @Override
                    public CandidateDto load(final Long key) throws Exception {
                        CandidateDto candidateDto = loadOneByIdFromDb(key);
                        if (log.isDebugEnabled()) {
                            log.debug("Cached objects ({}): {}", 1, candidateDto.toString());
                        }
                        return candidateDto;
                    }
                });
    }
}
