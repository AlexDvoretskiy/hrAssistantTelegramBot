package persistence.repositories.interfaces;


import persistence.entites.Candidate;

import java.util.List;

public interface CandidateRepository extends LoggedRepository<Candidate> {

    List<Candidate> findAll();

    Candidate findById(Long id);

    Candidate findByChatId(Long chatId);
}
