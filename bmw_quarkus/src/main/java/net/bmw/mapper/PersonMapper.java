package net.bmw.mapper;

import net.bmw.dto.PersonDto;
import net.bmw.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface PersonMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "age", target = "age")
//    @Mapping(source = "groups", target = "groups")
    PersonDto toDto(Person person);
    Person toEntity(PersonDto personDto);
    List<PersonDto> toDtoList(List<Person> persons);
    List<Person> toEntityList(List<PersonDto> personDtos);
}
