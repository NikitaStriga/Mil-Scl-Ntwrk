package q3df.mil.dto.role;

import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.entities.enums.SystemRoles;

@Data
@NoArgsConstructor
public class RoleDto {

    private SystemRoles role;

}
