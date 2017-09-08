package me.yling.w7challenge0908.repositories;

import me.yling.w7challenge0908.models.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobRepo extends CrudRepository<Job, Long> {
}
