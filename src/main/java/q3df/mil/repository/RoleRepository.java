package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.users_roles.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
