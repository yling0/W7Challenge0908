package me.yling.w7challenge0908.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long skiId;

    @NotEmpty
    @Size(min = 2)
    private String skiName;
    @NotEmpty
    @Size(min = 2)
    private String skiLevel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="person_id")
    private Person person;

    @ManyToMany(mappedBy = "jobskillSet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Job> jobSet;

    public long getSkiId() {
        return skiId;
    }

    public void setSkiId(long skiId) {
        this.skiId = skiId;
    }

    public String getSkiName() {
        return skiName;
    }

    public void setSkiName(String skiName) {
        this.skiName = skiName;
    }

    public String getSkiLevel() {
        return skiLevel;
    }

    public void setSkiLevel(String skiLevel) {
        this.skiLevel = skiLevel;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Job> getJobSet() {
        return jobSet;
    }

    public void setJobSet(Set<Job> jobSet) {
        this.jobSet = jobSet;
    }

    public Skill()
    {
        this.jobSet=new HashSet<Job>();
    }

    public void addJob (Job job)
    {
        jobSet.add(job);
    }
}
