package me.yling.w7challenge0908.repositories;

import me.yling.w7challenge0908.models.Job;
import me.yling.w7challenge0908.models.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface JobRepo extends CrudRepository<Job, Long> {
    Iterable<Job> findAllByJobTitleContains (String partialString);
    Iterable<Job> findAllByJobskillSet(Set<Skill>skillSet,Set<Skill>jobskillset);

}
