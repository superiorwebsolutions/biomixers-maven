package com.biomixers.event;

import com.biomixers.member.MemberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EventController {

    @Autowired
    MemberController memberController;

    @GetMapping("/events")
    public Map<Integer, Event> getEventCollection(){
        EventCollection eventCollection = new EventCollection(memberController.getAllMembers());
        eventCollection.updateAllPmcValues();

        System.out.println(eventCollection.getConfigTree());

        return eventCollection.getConfigTree();
    }
}
