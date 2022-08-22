package com.example.demo.freelancer;

import com.example.demo.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Freelancer {
    @Id
    @SequenceGenerator(
            name="freelancer_sequence",
            sequenceName = "freelancer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "freelancer_sequence"
    )
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String role;
    private String password;
    private String phone;
    private String personalInfo;
    @OneToMany(mappedBy = "freelancer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();
    private boolean isValidated;



    public Freelancer() {

    }

    public Freelancer(Long id, String name, String surname, String email, String role, String password, String phone, String personalInfo) {
        this.id = id;
        this.name = name;
        this.surname=surname;
        this.email = email;
        this.role=role;
        this.password=password;
        this.phone=phone;
        this.personalInfo=personalInfo;
        this.isValidated=false;


    }

    public Freelancer(String name, String surname, String email, String role, String password, String phone, String personalInfo) {
        this.name = name;
        this.surname=surname;
        this.email = email;
        this.role=role;
        this.password=password;
        this.phone=phone;
        this.personalInfo=personalInfo;
        this.isValidated=false;


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }
    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }
    public void addTask(Task t){
        this.tasks.add(t);
        t.setFreelancer(this);
    }



}
