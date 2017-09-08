package me.yling.w7challenge0908.repositories;

import me.yling.w7challenge0908.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface PerRepo extends CrudRepository<Person, Long> {

    Person findByUsername (String username);
    Person findByEmail (String email);
    Long countByEmail (String email);
    Long countByUsername (String username);

}
