package me.yling.w7challenge0908.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long jobId;

    @NotEmpty
    @Size(min = 2)
    private String jobTitle;
    @NotEmpty
    @Size(min = 2)
    private String employer;
    @NotEmpty
    @Size(min = 2)
    private String salary;
    @NotEmpty
    @Size(min = 2)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "job_id"),inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> jobskillSet;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Skill> getJobskillSet() {
        return jobskillSet;
    }

    public void setJobskillSet(Set<Skill> jobskillSet) {
        this.jobskillSet = jobskillSet;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Job()
    {
        this.jobskillSet = new HashSet<Skill>();
    }

    public void addSkitoJob (Skill skill)
    {
        this.jobskillSet.add(skill);
    }
}
