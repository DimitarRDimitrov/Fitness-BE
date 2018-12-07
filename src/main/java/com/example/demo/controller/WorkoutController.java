package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutController {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

    public Workout getWorkoutById(Integer workoutId) {
        Optional<Workout> workout = workoutRepository.findById(workoutId);
        if (workout.isPresent()) {
            return workout.get();
        }
        return null;
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> result = new ArrayList<>();
        workoutRepository.findAll().forEach(result::add);
        LocalDate currentDate = LocalDate.now();
        result = result.stream()
                .sorted((o1, o2) -> (int) (o1.getDate().toEpochDay() - o2.getDate().toEpochDay()))
                .filter(workout -> workout.getDate().isAfter(currentDate)).collect(Collectors.toList());
        return result;
    }

    public boolean applyUserForWorkout(Principal principal, Integer workoutId) {
        User user = userRepository.getUserByUserName(principal.getName());
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);

        if (user == null || !optionalWorkout.isPresent()) {
            return false;
        }

        Workout workout = optionalWorkout.get();
        if (workout.getApplicants().contains(user) || workout.getSpaceRemaining() == 0) {
            return false;
        }

        workout.getApplicants().add(user);
        workout.setSpaceRemaining(workout.getSpaceRemaining() - 1);
        workoutRepository.save(workout);
        return true;
    }

    public boolean removeUserFromWorkout(Principal principal, Integer workoutId) {
        User user = userRepository.getUserByUserName(principal.getName());
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);

        if (user == null || !optionalWorkout.isPresent()) {
            return false;
        }

        Workout workout = optionalWorkout.get();
        if (!workout.getApplicants().contains(user)) {
            return false;
        }

        workout.getApplicants().remove(user);
        workout.setSpaceRemaining(workout.getSpaceRemaining() + 1);
        workoutRepository.save(workout);
        return true;
    }

    public boolean createWorkout(String name, Integer duration, Integer trainerId, String date, String time, Integer capacity) throws ParseException {
        Optional<User> trainer = userRepository.findById(trainerId);
        Workout workout = new Workout(name, duration, trainer.get(), date, time, capacity);
        workoutRepository.save(workout);
        return true;
    }
}
