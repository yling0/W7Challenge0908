package me.yling.w7challenge0908.repositories;

import me.yling.w7challenge0908.models.Experience;
import me.yling.w7challenge0908.models.Job;
import org.springframework.data.repository.CrudRepository;

public interface ExpRepo extends CrudRepository<Experience, Long> {
    Iterable<Experience> findAllByCompany (String partialString);
}
