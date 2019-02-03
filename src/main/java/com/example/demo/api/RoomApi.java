package com.example.demo.api;

import com.example.demo.controller.RoomController;
import com.example.demo.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RoomApi {

    @Autowired
    RoomController roomController;

    @GetMapping(path = "room/all")
    public List<String> getAllRooms(){
        return roomController.getAllRooms().stream().map(Room::getName).collect(Collectors.toList());
    }
}
