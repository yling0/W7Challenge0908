package me.yling.w7challenge0908.services;

import me.yling.w7challenge0908.models.Person;
import me.yling.w7challenge0908.repositories.PerRepo;
import me.yling.w7challenge0908.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {


    @Autowired
    PerRepo perRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    public UserService (PerRepo perRepo)
    {
        this.perRepo=perRepo;
    }

    public Person findByEmail (String email)
    {
        return (Person) perRepo.findByEmail(email);
    }

    public Long countByEmail (String email)
    {
        return perRepo.countByEmail(email);
    }

    public Person findByUsername (String username)
    {
        return perRepo.findByUsername(username);
    }

    public void saveSeeker (Person person)
    {
        person.setRoles(Arrays.asList(roleRepo.findByRole("SEEKERS")));
        person.setEnabled(true);
        perRepo.save(person);
    }

    public void saveRecruiter (Person person)
    {
        person.setRoles(Arrays.asList(roleRepo.findByRole("RECRUITERS")));
        person.setEnabled(true);
        perRepo.save(person);
    }



}
