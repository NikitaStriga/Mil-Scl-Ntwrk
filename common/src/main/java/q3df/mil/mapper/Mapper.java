package q3df.mil.mapper;


import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * defines the general conversion logic
 * @param <E> from entity
 * @param <D> to entity
 */
public abstract class Mapper <E,D> {

    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    @Autowired
    public  ModelMapper modelMapper;


    public Mapper(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    /**
     * convert from entity to dto
     * @param entityClass entity Object
     * @return dto Object
     */
    public D toDto(E entityClass){
        return Objects.isNull(entityClass) ? null : modelMapper.map(entityClass,dtoClass);
    }

    /**
     * convert from dto to entity
     * @param dto dto Object
     * @return entity Object
     */
    public E fromDto(D dto){
        return Objects.isNull(dto) ? null : modelMapper.map(dto,entityClass);
    }

    /**
     * converter for missed setters
     * @return converter |entity,dto|
     */
    public Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapFromEntityToDto(source, destination);
            return context.getDestination();
        };
    }

    /**
     * converter for missed setters
     * @return converter |entity,dto|
     */
    public Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapFromDtoToEntity(source, destination);
            return context.getDestination();
        };
    }

    /**
     * logic for missed setter when the conversion comes from  entity to dto
     * @param source entity
     * @param destination dto
     */
    public void mapFromEntityToDto(E source, D destination){}


    /**
     * logic for missed setter when the conversion comes from  dto to entity
     * @param source entity
     * @param destination dto
     */
    public void mapFromDtoToEntity(D source, E destination){}


}
