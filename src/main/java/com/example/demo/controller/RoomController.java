package com.example.demo.controller;

import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        List<Room> allRooms = new ArrayList<>();
        roomRepository.findAll().forEach(allRooms::add);
        return allRooms;
    }
}
