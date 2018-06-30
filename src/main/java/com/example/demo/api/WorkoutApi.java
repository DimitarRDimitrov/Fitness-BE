package com.example.demo.api;

import com.example.demo.controller.WorkoutController;
import com.example.demo.entity.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.List;

@RestController
public class WorkoutApi {

    @Autowired
    WorkoutController workoutController;

    @CrossOrigin(origins = "*")
    @RequestMapping("/workouts/generated")
    public List<Workout> getAllWorkouts() {
        return workoutController.getAllWorkouts();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/workouts/add")
    public ResponseEntity<Workout> addWorkout(@PathParam("name") String name, @PathParam("duration") Integer duration,
                                              @PathParam("trainer") Integer trainerId, @PathParam("date") String date,
                                              @PathParam("time") String time, @PathParam("capacity") Integer capacity) {
//        WorkoutController workoutController = new WorkoutController();
        try {
            Workout workout = workoutController.addWorkout(name, duration, trainerId, date, time, capacity);
            return ResponseEntity.ok(workout);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
