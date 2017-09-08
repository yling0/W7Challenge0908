package me.yling.w7challenge0908.repositories;

import me.yling.w7challenge0908.models.Skill;
import org.springframework.data.repository.CrudRepository;

public interface SkiRepo extends CrudRepository<Skill, Long> {
}
