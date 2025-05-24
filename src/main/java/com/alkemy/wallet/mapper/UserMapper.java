package com.alkemy.wallet.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.alkemy.wallet.dto.UserCreateDTO;
import com.alkemy.wallet.dto.UserDTO;
import com.alkemy.wallet.models.user.User;

/**
 * Mapper para convertir entre la entidad User y su DTO.
 * Usa MapStruct para generar automáticamente el código de conversión.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { AccountMapper.class,
        PersonMapper.class, RoleMapper.class
})
public interface UserMapper {

    // Mapea User -> UserDTO (incluye también el Person mapeado automáticamente)
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.lastName", target = "lastName")
    @Mapping(source = "person.address", target = "address")
    @Mapping(source = "person.location", target = "location")
    @Mapping(source = "person.province", target = "province")
    @Mapping(source = "person.phoneNumber", target = "phoneNumber")
    @Mapping(source = "person.identityCard", target = "identityCard")
    @Mapping(source = "person.dateBirth", target = "dateBirth")
    @Mapping(target = "roles", expression = "java(mapRoles(user))")
    @Mapping(source = "accounts", target = "accounts")
    UserDTO toDTO(User user);

    default List<String> mapRoles(User user) {
        return user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getRoleName())
                .collect(Collectors.toList());
    }

    // Mapea UserDTO -> User (incluye también el Person mapeado automáticamente)
    @Mapping(target = "userRoles", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    User toEntity(UserDTO userDTO);

    // USER CREATE DTO
    UserCreateDTO toCreateDTO(User user);
    // Mapea UserCreateDTO -> User (incluye también el Person mapeado
    // automáticamente)

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) // Se setea manualmente luego
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "userRoles", ignore = true)
    User toEntity(UserCreateDTO userCreateDTO);
    // Mapea User -> UserCreateDTO (incluye también el Person mapeado
    // automáticamente)

}
