package com.example.demo.task;

import com.example.demo.freelancer.Freelancer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String info;
    private String type;
    private LocalDate dueTime;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "freelancer_id", nullable = false)
    @JsonIgnore
    private Freelancer freelancer;
    @Transient
    private int countTime;



    public Task(String title, String info, String type, LocalDate dueTime) {
        this.title = title;
        this.info = info;
        this.type = type;
        this.dueTime = dueTime;
    }
    public Task(Long id,String title, String info, String type, LocalDate dueTime) {
        this.id=id;
        this.title = title;
        this.info = info;
        this.type = type;
        this.dueTime = dueTime;
    }

    public Task() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalDate dueTime) {
        this.dueTime = dueTime;
    }
    public int getCountTime() {
        int result=0;
        result+=365*(Period.between(LocalDate.now(),this.dueTime).getYears());
        result+=30*(Period.between(LocalDate.now(),this.dueTime).getMonths());
        result+=Period.between(LocalDate.now(),this.dueTime).getDays();
        return result;
    }

    public void setCountTime(int countTime) {
        this.countTime = countTime;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }
}
