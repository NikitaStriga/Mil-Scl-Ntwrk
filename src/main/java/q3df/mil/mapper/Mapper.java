package q3df.mil.mapper;


import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;



public abstract class Mapper <E,D> {

    private final Class<E> entityClass;
    private final Class<D> dtoClass;


    @Autowired
    public  ModelMapper modelMapper;


    public Mapper(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    public D toDto(E entityClass){
        return Objects.isNull(entityClass) ? null : modelMapper.map(entityClass,dtoClass);
    }

    public E fromDto(D dto){
        return Objects.isNull(dto) ? null : modelMapper.map(dto,entityClass);
    }


    public Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapFromEntityToDto(source, destination);
            return context.getDestination();
        };
    }

    public Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapFromDtoToEntity(source, destination);
            return context.getDestination();
        };
    }

    public void mapFromEntityToDto(E source, D destination){};

    public void mapFromDtoToEntity(D source, E destination){};


}
