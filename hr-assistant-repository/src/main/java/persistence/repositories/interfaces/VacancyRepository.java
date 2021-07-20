package persistence.repositories.interfaces;



import persistence.entites.Vacancy;
import java.util.List;


public interface VacancyRepository extends LoggedRepository<Vacancy> {

    Vacancy findById(Long id);

    Vacancy findOneByTitleAndActiveTrue(String title);

    List<Vacancy> findAllByActiveTrue();

}
