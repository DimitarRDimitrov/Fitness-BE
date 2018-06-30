package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutController {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

    public Workout addWorkout(String name, Integer duration, Integer trainerId, String date, String time, Integer capacity) throws ParseException {
        Optional<User> trainer = userRepository.findById(trainerId);
        if (trainer.isPresent()) {
            Workout workout = new Workout(name, duration, trainer.get(), date, time, capacity);
            workoutRepository.save(workout);
            return workout;
        } else {
            throw new InvalidParameterException("Invalid trainerId");
        }
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> result = new ArrayList<>();
        workoutRepository.findAll().forEach(result::add);
        return result;
    }

}
