package com.example.demo.repository;

import com.example.demo.entity.WorkoutType;
import org.springframework.data.repository.CrudRepository;

public interface WorkoutTypeRepository extends CrudRepository<WorkoutType, Integer> {
    WorkoutType findWorkoutTypeByName(String name);
}
