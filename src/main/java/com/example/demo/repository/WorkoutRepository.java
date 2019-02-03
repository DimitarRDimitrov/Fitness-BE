package com.example.demo.repository;

import com.example.demo.entity.Workout;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends CrudRepository<Workout, Integer> {
    List<Workout> findAllByIsDeleted(boolean isDeleted);
    List<Workout> findAllByDate(LocalDate date);
}
