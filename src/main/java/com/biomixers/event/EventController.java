package com.biomixers.event;

import com.biomixers.BiomixersApplication;
import com.biomixers.filter.SearchFilterQuery;
import com.biomixers.member.MemberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EventController {

    @Autowired
    private MemberController memberController;

    @Autowired
    private EventService eventService;

    @Autowired
    private FinalEventCollectionRepository finalEventCollectionRepository;

    @GetMapping("/initial")
    public void start() throws Exception {
        memberController.generateSampleData();
    }

    @GetMapping("/final")
    public List<FinalEventCollection> getFinalEventCollection(){


        List<FinalEventCollection> finalEventCollection = eventService.getFinalEventCollection();

        // TODO:  Change to 0, 15 (this breaks it, since repeat members.  Find a way to make unique Ids for members, eventmemberattending, and events
        return finalEventCollection.subList(0, 5);
    }

    @GetMapping("/event/{eventId}")
    public Event getEvent(@PathVariable int eventId){
        EventCollection eventCollection = new EventCollection(memberController.getAllMembers());
        //System.out.println(memberController.getAllMembers().hashCode());

        Map<Integer, EventMemberAttending> members_attending = eventCollection.getEventById(eventId).getMembersAttending();

        //eventCollection.updateAllPmcValues();

        //EventCollection tempCollection = eventCollection.duplicate


        return eventCollection.getEventById(eventId);
    }

    @PostMapping("/final-event-collection")
    public void addFinalEventCollection(@RequestBody FinalEventCollection finalEventCollection){
        finalEventCollectionRepository.save(finalEventCollection);


    }
/*
    @GetMapping("/event/1")
    public Map<String, EventMemberAttending> getEvent(){
        EventCollection eventCollection = new EventCollection(memberController.getAllMembers());
        eventCollection.updateAllPmcValues();
        return HelperFunctions.convertMap((Map<Integer, EventMemberAttending>) eventCollection.getEventById(1).getMembersAttending());
    }

 */

    // Put this in QueryController?
    @PostMapping("/filter")
    public ResponseEntity<SearchFilterQuery> filterResults(@RequestBody HashMap<String, Object> responseData) {

        SearchFilterQuery searchFilterQuery = new SearchFilterQuery();
        searchFilterQuery.setMinAllowedPerRestaurant(Integer.parseInt(responseData.get("minAllowedPerRestaurant").toString()));
        searchFilterQuery.setMaxAllowedPerRestaurant(Integer.parseInt(responseData.get("maxAllowedPerRestaurant").toString()));
        //searchFilterQuery.setNumDaysOfAvailability(Integer.parseInt(responseData.get("numDaysOfAvailability").toString()));
        //searchFilterQuery.setNumFoodPreferences(Integer.parseInt(responseData.get("numFoodPreferences").toString()));
        searchFilterQuery.setPercentageOfMembersMet(Float.parseFloat(responseData.get("percentageOfMembersMet").toString()));
        searchFilterQuery.setRandomizeResults(Boolean.parseBoolean(responseData.get("randomizeResults").toString()));

        BiomixersApplication.setSearchFilterQuery(searchFilterQuery);


        //eventService.getFinalEventCollection();


        return new ResponseEntity<SearchFilterQuery>(searchFilterQuery, HttpStatus.OK);
    }
}
