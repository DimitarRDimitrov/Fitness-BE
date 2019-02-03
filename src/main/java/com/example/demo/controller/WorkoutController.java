package com.example.demo.controller;

import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import com.example.demo.entity.WorkoutType;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkoutRepository;
import com.example.demo.repository.WorkoutTypeRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkoutController {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkoutTypeRepository workoutTypeRepository;

    @Autowired
    RoomRepository roomRepository;

    public Workout getWorkoutById(Integer workoutId) {
        Optional<Workout> workout = workoutRepository.findById(workoutId);
        if (workout.isPresent()) {
            return workout.get();
        }
        return null;
    }

    public List<Workout> getRoomWorkouts(String roomName, String date) {
        List<Workout> result = new ArrayList<>();

        Room room = roomRepository.findRoomByName(roomName);
        List<Integer> dateValues = Arrays.stream(date.split("-")).map(Integer::valueOf).collect(Collectors.toList());
        LocalDate workoutDate = LocalDate.of(dateValues.get(0), dateValues.get(1), dateValues.get(2));
        workoutRepository.findAllByDate(workoutDate).forEach(result::add);
        result.removeIf(workout -> !workout.getRoom().getId().equals(room.getId()));

        return result;
    }

    public List<Workout> getAllWorkouts(Boolean startDateNow) {
        List<Workout> result = new ArrayList<>();
        workoutRepository.findAllByIsDeleted(false).forEach(result::add);

        result = result.stream()
                .sorted((o1, o2) -> (int) (o1.getDate().toEpochDay() - o2.getDate().toEpochDay()))
                .collect(Collectors.toList());

        filterByStartDateNow(startDateNow, result);
        return result;
    }

    private void filterByStartDateNow(Boolean startDateNow, List<Workout> result) {
        if (startDateNow == null || !startDateNow) {
            return;
        }
        LocalDate currentDate = LocalDate.now();
        result.removeIf(workout -> !workout.getDate().isAfter(currentDate.minusDays(1)));
        result.removeIf(workout -> !workout.getDate().isBefore(currentDate.plusDays(7)));
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
        try {
            workoutRepository.save(workout);
        } catch (Exception e) {
            return false;
        }
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

    public boolean createWorkout(String name, Integer duration, String trainerUsername, String date, String time, Integer capacity, String dateTo, String workoutType, String roomName) throws ParseException {
        User trainer = userRepository.getUserByUserName(trainerUsername);
        if (trainer == null) {
            return false;
        }
        WorkoutType woType = workoutTypeRepository.findWorkoutTypeByName(workoutType);
        Room room = roomRepository.findRoomByName(roomName);
        if (woType == null || room == null) {
            return false;
        }
        if (!checkIfTimeIsFree(date, time, duration, room.getId())){
            return false;
        }

        if (null != dateTo && !dateTo.isEmpty()) {
            String[] dateValues = date.split("-");
            LocalDate dateFrom = LocalDate.of(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
            String[] dateToValues = dateTo.split("-");
            LocalDate dateUntil = LocalDate.of(Integer.valueOf(dateToValues[0]), Integer.valueOf(dateToValues[1]), Integer.valueOf(dateToValues[2]));
            if (dateFrom.isAfter(dateUntil)) {
                return false;
            }

            int days = dateFrom.until(dateUntil).getDays();
            for (int i = 0; i <= days; i++) {
                workoutRepository.save(new Workout(name, duration, trainer, dateFrom.plusDays(i), time, capacity, woType, room));
            }
        } else {
            Workout workout = new Workout(name, duration, trainer, date, time, capacity, woType, room);
            workoutRepository.save(workout);
        }
        return true;
    }

    private boolean checkIfTimeIsFree(String date, String time, Integer duration, Integer roomId){

        List<Workout> result = new ArrayList<>();
        List<Integer> dateValues = Arrays.stream(date.split("-")).map(Integer::valueOf).collect(Collectors.toList());
        LocalDate workoutDate = LocalDate.of(dateValues.get(0), dateValues.get(1), dateValues.get(2));
        workoutRepository.findAllByDate(workoutDate).forEach(result::add);
        result.removeIf(workout -> !workout.getRoom().getId().equals(roomId));

        String[] timeFromQuery = time.split(":");
        int workoutMinutesFromQuery = Integer.parseInt(timeFromQuery[0]) * 60 + Integer.parseInt(timeFromQuery[1]);

        for(Workout workout : result){
            LocalTime workoutTime = workout.getTime().toLocalTime();
            int workoutMinutes = workoutTime.getHour() * 60 + workoutTime.getMinute();

            if ((workoutMinutesFromQuery > workoutMinutes && workoutMinutesFromQuery < (workoutMinutes + workout.getDuration()))
                    || ((workoutMinutesFromQuery + duration) > workoutMinutes && (workoutMinutesFromQuery + duration) < (workoutMinutes + workout.getDuration()))) {
                return false;
            }
        }

        return true;
    }

    public List<Workout> getUpcomingUserWorkouts(Principal principal) {
        User user = userRepository.getUserByUserName(principal.getName());
        List<Workout> userWorkouts = user.getUserWorkouts().stream()
                .sorted((o1, o2) -> (int) (o1.getDate().toEpochDay() - o2.getDate().toEpochDay()))
                .collect(Collectors.toList());

        filterByStartDateNow(true, userWorkouts);
        return userWorkouts;
    }

    public boolean deleteWorkout(Principal principal, Integer workoutId) {
        Workout workout = workoutRepository.findById(workoutId).orElse(null);
        if (workout == null) {
            return false;
        }
        User trainer = userRepository.getUserByUserName(principal.getName());
        if (trainer == null) {
            return false;
        }
        if (!Objects.equals(workout.getTrainer().getId(), trainer.getId())) {
            return false;
        }
        workout.setDeleted(true);
        workoutRepository.save(workout);
        workout.getApplicants().forEach(user -> {user.setHasNotification(true);
            userRepository.save(user);});
        return true;
    }

    public Set<User> getWorkoutParticipants(Integer workoutId) {
        Workout workout = workoutRepository.findById(workoutId).orElse(null);
        if (workout == null) {
            return null;
        }
        return workout.getApplicants();

    }
}
