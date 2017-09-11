package me.yling.w7challenge0908.repositories;

import me.yling.w7challenge0908.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface PerRepo extends CrudRepository<Person, Long> {

    Person findByUsername (String username);
    Person findByEmail (String email);
    Person findAllByUsername(String username);
    Long countByEmail (String email);
    Long countByUsername (String username);

    Iterable<Person> findAllByEducationSet_uni (String uni);
    Iterable<Person> findAllByExperienceSet_company (String company);
    Iterable<Person> findAllByFName (String fName);
    Iterable<Person> findAllByLName (String lName);


}
