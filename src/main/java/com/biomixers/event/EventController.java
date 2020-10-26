package com.biomixers.event;

import com.biomixers.BiomixersApplication;
import com.biomixers.filter.SearchFilterQuery;
import com.biomixers.member.Member;
import com.biomixers.member.MemberController;
import com.biomixers.util.HelperFunctions;
import org.hibernate.id.uuid.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class EventController {

    @Autowired
    private MemberController memberController;

    private SearchFilterQuery searchFilterQuery;


    @GetMapping("/final")
    public HashMap<Integer, FinalEventCollection> getFinalEventCollection(){

        EventCollection eventCollection = new EventCollection();
        List<Member> membersList = memberController.getAllMembers();

        HashMap<Integer, FinalEventCollection> allRuns = new HashMap<>();

        int maxRuns = 10;

        BiomixersApplication.getSearchFilterQuery().setMinAllowedPerRestaurant(4);
        BiomixersApplication.getSearchFilterQuery().setMaxAllowedPerRestaurant(15);

        int membersMaxMinAllowedRange = BiomixersApplication.getSearchFilterQuery().getMaxAllowedPerRestaurant() - BiomixersApplication.getSearchFilterQuery().getMinAllowedPerRestaurant() - 2;

        int numSteps = Math.round(maxRuns / membersMaxMinAllowedRange);

        if(numSteps == 0)
            numSteps = 1;

        int descCount = 0;

        int maxNumberOfMembersAllowedPerRestaurant = BiomixersApplication.getSearchFilterQuery().getMaxAllowedPerRestaurant();

        for(int i = 1; i < maxRuns + 1; i++){
            int maxActiveConfigs = numSteps - descCount;

            FinalEventCollection finalEventCollection = EventCollection.runSorter(membersList, maxActiveConfigs);
            finalEventCollection.setMaxActiveConfigs(maxActiveConfigs);
            finalEventCollection.setMaxMembersAllowedPerRestaurant(maxNumberOfMembersAllowedPerRestaurant);

            allRuns.put(i, finalEventCollection);

            descCount += 1;

            if((float) i % numSteps == 0.0){
                maxNumberOfMembersAllowedPerRestaurant -= 1;
                descCount = 0;
            }

            // TODO:  slowly lower max_number_of_members_allowed_per_restaurant
            // TODO:  slowly increase ['MAX_NumberOfMembersMetAlreadyAllowance'] (maybe?)

            Set<String> temp = new HashSet<>();


        }

        System.out.println(allRuns.toString());

        for(Event event : eventCollection.getConfigTree().values()){
            //System.out.println(event.getCount());
        }


        return allRuns;
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

    // Put this in QueryController?
    @PostMapping("/filter")
    public ResponseEntity<SearchFilterQuery> filterResults(@RequestBody SearchFilterQuery filter) {

                /*
    // USE THIS FOR TESTING THE POST
    {
"minAllowedPerRestaurant": 6,
"maxAllowedPerRestaurant": 12,
"numDaysOfAvailability": 3,
"numFoodPreferences": 3,
  "randomizeResults": false
}
     */

        EventCollection eventCollection = new EventCollection(memberController.getAllMembers());
        //eventCollection.setSearchFilterQuery(filter);

        System.out.println(searchFilterQuery.toString());
        memberController.generateSampleData();
        memberController.getAllMembers();


        return new ResponseEntity<SearchFilterQuery>(searchFilterQuery, HttpStatus.OK);
    }
}
