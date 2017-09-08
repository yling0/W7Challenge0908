package me.yling.w7challenge0908.repositories;

import me.yling.w7challenge0908.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Long> {
    Role findByRole (String role);
}
