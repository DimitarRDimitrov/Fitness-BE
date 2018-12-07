package com.example.demo.api;

import com.example.demo.controller.WorkoutController;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@RestController
public class WorkoutApi {

    @Autowired
    WorkoutController workoutController;

    @CrossOrigin(origins = "*")
    @RequestMapping("/workouts/{workoutId}")
    public Workout getWorkoutById(@PathVariable("workoutId") Integer workoutId) {
        return workoutController.getWorkoutById(workoutId);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/workouts/all")
    public List<Workout> getAllWorkouts() {
        return workoutController.getAllWorkouts();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/workouts/apply")
    public boolean applyUserForWorkout(Principal principal, @PathParam("workoutId") Integer workoutId) {
        return workoutController.applyUserForWorkout(principal, workoutId);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/workouts/remove")
    public boolean removeUserFromWorkout(Principal principal, @PathParam("workoutId") Integer workoutId) {
        return workoutController.removeUserFromWorkout(principal, workoutId);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("workouts/create")
    public boolean createWorkout(@RequestBody WorkoutDetails workoutDetails) throws ParseException {
        return workoutController.createWorkout(workoutDetails.getName(), workoutDetails.getDuration(), workoutDetails.getTrainer(), workoutDetails.getDate(),
                workoutDetails.getTime(), workoutDetails.getCapacity());
    }

    public static class WorkoutDetails {
        private String name;
        private Integer duration;
        private Integer capacity;
        private String date;
        private String time;
        private Integer trainer;

        public WorkoutDetails() {

        }

        public WorkoutDetails(String name, Integer duration, Integer capacity, String date, String time, Integer trainer) {
            this.name = name;
            this.duration = duration;
            this.capacity = capacity;
            this.date = date;
            this.time = time;
            this.trainer = trainer;
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

        public Integer getCapacity() {
            return capacity;
        }

        public void setCapacity(Integer capacity) {
            this.capacity = capacity;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Integer getTrainer() {
            return trainer;
        }

        public void setTrainer(Integer trainer) {
            this.trainer = trainer;
        }
    }
}
