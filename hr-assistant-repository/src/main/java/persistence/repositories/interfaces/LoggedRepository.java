package persistence.repositories.interfaces;


public interface LoggedRepository<T> {
    String SQL_STATEMENT = "SQL запрос: ";
    String SQL_SAVE_STATEMENT = "Успешно сохранен объект: ";
    String SQL_UPDATE_STATEMENT = "Успешно обновлен объект: ";
    String SQL_DELETE_STATEMENT = "Успешно удален объект: ";

    void save(T t);

    void update(T t);

    void merge(T t);

    void delete(T t);

}
