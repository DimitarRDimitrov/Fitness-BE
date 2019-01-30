package com.example.demo.api;

import com.example.demo.controller.WorkoutTypeController;
import com.example.demo.entity.WorkoutType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WorkoutTypeApi {

    @Autowired
    WorkoutTypeController workoutTypeController;

    @GetMapping(path = "workout-type/all")
    public List<String> getAllWorkoutTypes() {
        return workoutTypeController.getAllWorkoutTypes().stream().map(WorkoutType::getName).collect(Collectors.toList());
    }

    @PostMapping(path = "workout-type/create")
    public boolean createWorkoutType(@PathParam("name") String name) {
        return workoutTypeController.createWorkoutType(name);
    }
}
