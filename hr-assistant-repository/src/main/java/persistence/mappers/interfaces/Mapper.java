package persistence.mappers.interfaces;


public interface Mapper<E, D> {

    D mapToDto(E e);

    E mapToEntity(D d);

}
