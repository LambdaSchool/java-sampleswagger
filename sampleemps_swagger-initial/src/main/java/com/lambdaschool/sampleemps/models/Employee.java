package com.lambdaschool.sampleemps.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees")
@JsonIgnoreProperties(value = {"hasvalueforsalary"})
public class Employee extends Auditable
{
    @Id // The primary key
    @GeneratedValue(strategy = GenerationType.AUTO) // We will let the database decide how to generate it
    private long employeeid; // long so we can have many rows

    @Column(nullable = false,
            unique = true)
    private String name;

    @Transient
    public boolean hasvalueforsalary = false;
    private double salary;

    /*
     * emp is the field from EmployeeTitles
     * CascadeType.ALL says that when we add, update, delete an Employee record, have that affect emp in EmployeeTitle.
     * Notice that in EmployeeTitle there is no cascade option. This way manipulating an Employee record only affects
     * the EmployeeTitle join table but does not affect the JobTitle table.
     */
    @OneToMany(mappedBy = "emp",
            cascade = CascadeType.ALL)
    /*
     * When displaying EmployeeTitles from the Employee class, do not display the employee again.
     * However do allow for data to be added to the emp field in EmployeeTitles
     */
    @JsonIgnoreProperties(value = "emp",
            allowSetters = true)
    /*
     * We know all of this works with EmployeeTitles because that is the class of the field name that making the One To Many relationship!
     * This array contains the list of EmployeeTitles assigned to this Employee
     */
    private Set<EmployeeTitles> jobnames = new HashSet<>();

    @OneToMany(mappedBy = "employee",
            cascade = CascadeType.ALL,
            // when adding, reading, updating, and delete, the operations should affect the emails table as well)
            orphanRemoval = true)
    // if we find a email that has a reference to an employee that does not exist, delete that email record
    // we want to ignore, not display, the employee object found in Email
    @JsonIgnoreProperties(value = "employee")
    private List<Email> emails = new ArrayList<>();

    public Employee()
    {
        // the default constructor is required by the JPA
    }

    public Employee(
            String name,
            double salary)
    {
        this.name = name;
        this.salary = salary;
    }

    public long getEmployeeid()
    {
        return employeeid;
    }

    public void setEmployeeid(long employeeid)
    {
        this.employeeid = employeeid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getSalary()
    {
        return salary;
    }

    public void setSalary(double salary)
    {
        hasvalueforsalary = true;
        this.salary = salary;
    }

    public List<Email> getEmails()
    {
        return emails;
    }

    public void setEmails(List<Email> emails)
    {
        this.emails = emails;
    }

    public Set<EmployeeTitles> getJobnames()
    {
        return jobnames;
    }

    public void setJobnames(Set<EmployeeTitles> jobnames)
    {
        this.jobnames = jobnames;
    }
}