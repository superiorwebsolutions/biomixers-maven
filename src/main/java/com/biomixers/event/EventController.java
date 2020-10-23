package com.biomixers.event;

import com.biomixers.member.Member;
import com.biomixers.member.MemberController;
import com.biomixers.util.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EventController {

    @Autowired
    MemberController memberController;

    @GetMapping("/events")
    public Map<Integer, Event> getEventCollection(){



        EventCollection eventCollection = new EventCollection(memberController.getAllMembers());
        eventCollection.updateAllPmcValues();



        return eventCollection.getConfigTree();
    }

    @GetMapping("/event/{eventId}")
    public Event getEvent(@PathVariable int eventId){
        EventCollection eventCollection = new EventCollection(memberController.getAllMembers());
        //System.out.println(memberController.getAllMembers().hashCode());

        HashMap<Integer, EventMemberAttending> members_attending = eventCollection.getEventById(eventId).getMembersAttending();


        //eventCollection.updateAllPmcValues();

        //EventCollection tempCollection = eventCollection.duplicate


        return eventCollection.getEventById(eventId);
    }
/*
    @GetMapping("/event/1")
    public Map<String, EventMemberAttending> getEvent(){
        EventCollection eventCollection = new EventCollection(memberController.getAllMembers());
        eventCollection.updateAllPmcValues();
        return HelperFunctions.convertMap((Map<Integer, EventMemberAttending>) eventCollection.getEventById(1).getMembersAttending());
    }

 */
}
