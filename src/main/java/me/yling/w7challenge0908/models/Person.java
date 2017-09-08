package me.yling.w7challenge0908.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table (name = "PERSON_DATA")
public class Person {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min = 2)
    @Column (name = "first_name")
    private String fName;

    @NotEmpty
    @Size(min = 2)
    @Column (name = "last_name")
    private String lName;

    @NotEmpty
    @Email
    @Column (name = "email", nullable = false)
    private String email;

    @NotEmpty
    @Column (name = "username")
    private String username;

    @NotEmpty
    @Column (name = "password")
    private String password;

    @Column (name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Education> educationSet;

    @OneToMany (mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Experience> experienceSet;

    @OneToMany (mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Job> jobSet;

    @OneToMany (mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Skill> skillSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Education> getEducationSet() {
        return educationSet;
    }

    public void setEducationSet(Set<Education> educationSet) {
        this.educationSet = educationSet;
    }

    public Set<Experience> getExperienceSet() {
        return experienceSet;
    }

    public void setExperienceSet(Set<Experience> experienceSet) {
        this.experienceSet = experienceSet;
    }

    public Set<Job> getJobSet() {
        return jobSet;
    }

    public void setJobSet(Set<Job> jobSet) {
        this.jobSet = jobSet;
    }

    public Set<Skill> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(Set<Skill> skillSet) {
        this.skillSet = skillSet;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
