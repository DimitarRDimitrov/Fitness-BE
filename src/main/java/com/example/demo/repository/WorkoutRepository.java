package com.example.demo.repository;

import com.example.demo.entity.Workout;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends CrudRepository<Workout, String> {
    List<Workout> findWorkoutByName(String name);
}
