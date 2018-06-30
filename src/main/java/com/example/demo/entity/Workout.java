package com.example.demo.entity;

import sun.plugin.dom.exception.InvalidStateException;

import javax.persistence.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Entity(name = "workout")
public class Workout {
    @Id
    @Column(name = "workout_id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "duration")
    private Integer duration;
    @ManyToOne()
    @JoinColumn(name = "trainerId", referencedColumnName = "id")
    private User trainer;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "time")
    private Time time;
    @Column
    private Integer capacity;
    @Column
    private Integer spaceRemaining;

    public Workout() {
    }

    public Workout(String name, Integer duration, User trainer, String date, String time, Integer capacity) throws ParseException {
        this.name = name;
        this.duration = duration;
        this.trainer = trainer;
        String[] dateValues = date.split("-");
        this.date = LocalDate.of(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
        this.time = new Time(new SimpleDateFormat("HH:mm").parse(time).getTime());
        this.capacity = capacity;
        this.spaceRemaining = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    public LocalDate getDate() {
        return LocalDate.from(this.date);
    }

    public void setDate(String date) throws ParseException {
        String[] dateValues = date.split("-");
        this.date = LocalDate.of(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(String time) throws ParseException {
        this.time = new Time(new SimpleDateFormat("HH:mm").parse(time).getTime());
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void reserveOneSpace() {
        if (this.spaceRemaining - 1 < 0) {
            throw new InvalidStateException("No free space remaining");
        }
        this.spaceRemaining--;
    }
}
