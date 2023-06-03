package pl.krax.charity.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.krax.charity.dto.UserDto;
import pl.krax.charity.dto.UserRegisterDto;
import pl.krax.charity.entities.Role;
import pl.krax.charity.entities.User;
import pl.krax.charity.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    User toEntity(UserDto userDto);

    @Mapping(target = "email", source = "email")
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", constant = "")
    @Mapping(target = "password", expression = "java(hashPassword(passwordEncoder, userRegisterDto))")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "activeAccount", constant = "0")
    @Mapping(target = "roles", expression = "java(getDefaultRoles(roleRepository))")
    User toUser(UserRegisterDto userRegisterDto, @Context RoleRepository roleRepository,
                @Context BCryptPasswordEncoder passwordEncoder);

    default Set<Role> getDefaultRoles(RoleRepository roleRepository) {
        Set<Role> defaultRoles = new HashSet<>();
        Role userRole = roleRepository.findByRoleName("ROLE_USER");
        if (userRole != null) {
            defaultRoles.add(userRole);
        }
        return defaultRoles;
    }

    default String hashPassword(BCryptPasswordEncoder passwordEncoder, UserRegisterDto userRegisterDto) {
        return passwordEncoder.encode(userRegisterDto.getPassword());
    }
}
