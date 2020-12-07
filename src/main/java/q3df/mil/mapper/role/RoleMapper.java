package q3df.mil.mapper.role;


import org.springframework.stereotype.Component;
import q3df.mil.dto.role.RoleDto;
import q3df.mil.entities.role.Role;
import q3df.mil.mapper.Mapper;

@Component
public class RoleMapper extends Mapper<Role, RoleDto> {

    public RoleMapper() {
        super(Role.class, RoleDto.class);
    }

}
