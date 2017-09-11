package me.yling.w7challenge0908.repositories;

import me.yling.w7challenge0908.models.Education;
import org.springframework.data.repository.CrudRepository;

public interface EduRepo extends CrudRepository<Education, Long> {
    Iterable<Education> findAllByUni (String partialString);
}
