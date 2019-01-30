package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "workout_type")
public class WorkoutType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "workoutType")
    List<Workout> workoutList;

    public WorkoutType() {
    }

    public WorkoutType(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Workout> getWorkoutList() {        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }
}
