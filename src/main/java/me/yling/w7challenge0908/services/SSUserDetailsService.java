package me.yling.w7challenge0908.services;

import me.yling.w7challenge0908.models.Person;
import me.yling.w7challenge0908.models.Role;
import me.yling.w7challenge0908.repositories.PerRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService{

    private PerRepo perRepo;

    public SSUserDetailsService(PerRepo perRepo)
    {
        this.perRepo=perRepo;
    }

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException
    {
        try
        {
            Person person = (Person) perRepo.findByUsername(username);
            if (person == null)
            {
                System.out.println("person not found with the provided username" + person.toString());
                return null;
            }

            System.out.println(" user from username: " + person.toString());
            return new User (person.getUsername(), person.getPassword(), getAuthorities(person));

        } catch (Exception e)

        {
            throw new UsernameNotFoundException("User not found");
        }

    }

    private Set<GrantedAuthority> getAuthorities(Person person)
    {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (Role role : person.getRoles())
        {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        System.out.println("user authorities are " + authorities.toString());
        return authorities;
    }






}
