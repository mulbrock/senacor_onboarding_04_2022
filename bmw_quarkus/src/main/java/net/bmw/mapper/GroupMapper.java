package net.bmw.mapper;

import net.bmw.dto.GroupDto;
import net.bmw.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "cdi")
public interface GroupMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "creatingTime", target = "creationDate")
    @Mapping(source = "meetingTime", target = "meetingDate")
//    @Mapping(source = "persons", target = "persons")
    GroupDto toDto(Group group);
    Group toEntity(GroupDto groupDto);
    List<GroupDto> toDtoList(List<Group> groups);
    List<Group> toEntityList(List<GroupDto> groupDtos);
    Set<GroupDto> toDtoSet(Set<Group> groups);

}
