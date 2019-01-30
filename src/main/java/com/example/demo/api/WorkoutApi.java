package com.example.demo.api;

import com.example.demo.controller.WorkoutController;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import com.example.demo.entity.WorkoutType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

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
    public List<Workout> getAllWorkouts(@PathParam("startDateNow") Boolean startDateNow) {
        return workoutController.getAllWorkouts(startDateNow);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/workouts/user")
    public List<Workout> getWorkoutsByUser(Principal principal) {
        return workoutController.getUpcomingUserWorkouts(principal);
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
        return workoutController.createWorkout(workoutDetails.getName(), workoutDetails.getDuration(), workoutDetails.getTrainerUsername(), workoutDetails.getDate(),
                workoutDetails.getTime(), workoutDetails.getCapacity(), workoutDetails.dateTo, workoutDetails.workoutType);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("workouts/delete")
    public boolean deleteWorkout(Principal principal, @PathParam("workoutId") Integer workoutId) {
        return workoutController.deleteWorkout(principal, workoutId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/workouts/find")
    public Set<User> getWorkoutParticipants(@PathParam("workoutId") Integer workoutId) {
        return workoutController.getWorkoutParticipants(workoutId);
    }

    public static class WorkoutDetails {
        private String name;
        private Integer duration;
        private Integer capacity;
        private String date;
        private String time;
        private String trainerUsername;
        private String dateTo;
        private String workoutType;

        public WorkoutDetails() {

        }

        public WorkoutDetails(String name, Integer duration, Integer capacity, String date, String time, String trainerUsername, String dateTo, String workoutType) {
            this.name = name;
            this.duration = duration;
            this.capacity = capacity;
            this.date = date;
            this.time = time;
            this.trainerUsername = trainerUsername;
            this.dateTo = dateTo;
            this.workoutType = workoutType;
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

        public String getTrainerUsername() {
            return trainerUsername;
        }

        public void setTrainerUsername(String trainerUsername) {
            this.trainerUsername = trainerUsername;
        }

        public String getDateTo() {
            return dateTo;
        }

        public void setDateTo(String dateTo) {
            this.dateTo = dateTo;
        }

        public String getWorkoutType() {
            return workoutType;
        }

        public void setWorkoutType(String workoutType) {
            this.workoutType = workoutType;
        }
    }
}
