package com.example.demo.controller;

import com.example.demo.entity.WorkoutType;
import com.example.demo.repository.WorkoutTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutTypeController {

    @Autowired
    private WorkoutTypeRepository workoutTypeRepository;

    public List<WorkoutType> getAllWorkoutTypes() {
        List<WorkoutType> allTypes = new ArrayList<>();
        workoutTypeRepository.findAll().forEach(allTypes::add);
        return allTypes;
    }

    public WorkoutType getWorkoutTypeById(Integer id) {
        Optional<WorkoutType> workoutTypeOptional = workoutTypeRepository.findById(id);
        if (!workoutTypeOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid workout type id!");
        }
        return workoutTypeOptional.get();
    }

    public boolean createWorkoutType(String workoutTypeName) {
        WorkoutType workoutType = new WorkoutType(workoutTypeName);
        workoutTypeRepository.save(workoutType);
        return true;
    }
}
