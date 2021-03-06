package com.example.demo.entity;

import javax.persistence.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity(name = "workout")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "workout_id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "duration")
    private Integer duration;
    @ManyToOne()
    @JoinColumn(name = "trainer_username", referencedColumnName = "name")
    private User trainer;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "time")
    private Time time;
    @Column
    private Integer capacity;
    @Column
    private Integer spaceRemaining;
    @Column
    private boolean isDeleted;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_workouts", joinColumns = @JoinColumn(name = "workout_id"), inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<User> applicants;

    @ManyToOne()
    @JoinColumn(name = "workout_type_id")
    private WorkoutType workoutType;

    @ManyToOne()
    @JoinColumn(name = "room_id")
    private Room room;

    @Version
    private Long version;

    public Workout() {
    }

    public Workout(String name, Integer duration, User trainer, String date, String time, Integer capacity, WorkoutType workoutType, Room room) throws ParseException {
        this.name = name;
        this.duration = duration;
        this.trainer = trainer;
        String[] dateValues = date.split("-");
        this.date = LocalDate.of(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
        this.time = new Time(new SimpleDateFormat("HH:mm").parse(time).getTime());
        this.capacity = capacity;
        this.spaceRemaining = capacity;
        this.workoutType = workoutType;
        this.room = room;
    }

    public Workout(String name, Integer duration, User trainer, LocalDate date, String time, Integer capacity, WorkoutType workoutType, Room room) throws ParseException {
        this.name = name;
        this.duration = duration;
        this.trainer = trainer;
        this.date = date;
        this.time = new Time(new SimpleDateFormat("HH:mm").parse(time).getTime());
        this.capacity = capacity;
        this.spaceRemaining = capacity;
        this.workoutType = workoutType;
        this.room = room;
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

    public Time getTime() {
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getSpaceRemaining() {
        return spaceRemaining;
    }

    public void setSpaceRemaining(Integer spaceRemaining) {
        this.spaceRemaining = spaceRemaining;
    }

    public Set<User> getApplicants() {
        return applicants;
    }

    public void setApplicants(Set<User> applicants) {
        this.applicants = applicants;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(WorkoutType workoutType) {
        this.workoutType = workoutType;
    }

    public Room getRoom() { return room; }

    public void setRoom(Room room) {  this.room = room; }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
