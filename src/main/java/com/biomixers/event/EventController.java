package com.biomixers.event;

import com.biomixers.BiomixersApplication;
import com.biomixers.filter.SearchFilterQuery;
import com.biomixers.member.Member;
import com.biomixers.member.MemberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EventController {

    @Autowired
    private MemberController memberController;

    private SearchFilterQuery searchFilterQuery;


    @GetMapping("/final")
    public List<FinalEventCollection> getFinalEventCollection(){

        EventCollection eventCollection = new EventCollection();
        List<Member> membersList = memberController.getAllMembers();

        HashMap<Integer, FinalEventCollection> allRuns = new HashMap<>();

        int maxRuns = 200;

        //BiomixersApplication.getSearchFilterQuery().setMinAllowedPerRestaurant(4);
        //BiomixersApplication.getSearchFilterQuery().setMaxAllowedPerRestaurant(15);

        int membersMaxMinAllowedRange = BiomixersApplication.getSearchFilterQuery().getMaxAllowedPerRestaurant() - BiomixersApplication.getSearchFilterQuery().getMinAllowedPerRestaurant() - 2;

        int numSteps = Math.round(maxRuns / membersMaxMinAllowedRange);

        if(numSteps == 0)
            numSteps = 1;


        int descCount = 0;

        int maxNumberOfMembersAllowedPerRestaurant = BiomixersApplication.getSearchFilterQuery().getMaxAllowedPerRestaurant();

        int maxActiveConfigs;

        for(int i = 1; i < maxRuns + 1; i++){
            maxActiveConfigs = numSteps - descCount;

            FinalEventCollection finalEventCollection = EventCollection.runSorter(membersList, maxActiveConfigs);
            finalEventCollection.setMaxActiveConfigs(maxActiveConfigs);
            finalEventCollection.setMaxMembersAllowedPerRestaurant(maxNumberOfMembersAllowedPerRestaurant);

            allRuns.put(finalEventCollection.hashCode(), finalEventCollection);

            descCount += 1;

            if(((float) i % numSteps) == 0.0){
                maxNumberOfMembersAllowedPerRestaurant -= 1;
                descCount = 0;


            }

            // TODO:  slowly lower max_number_of_members_allowed_per_restaurant?
            // TODO:  slowly increase ['MAX_NumberOfMembersMetAlreadyAllowance'] (maybe?)



        }

        System.out.println(allRuns.toString());

        // TODO:  write this code to order results
        /*
        temp = []
    res = defaultdict(dict)
    for key, data in all_runs.items():
        skip_this_config = False
        temp_str = ''

        for config_id, temp_config in data['config_tree'].items():
            # Do not include this result if the number of members attending is greater than allowed

            if temp_config['count'] > gvs.GLOBALS['MAX_NumberOfMembersAllowedPerRestaurant']:
                skip_this_config = True

           # pprint.pprint(temp_config)
            temp_str += str(temp_config['config_id'])

        unique_config_identifier = str(data['total_pmc']) + temp_str

        if unique_config_identifier not in temp:
            temp.append(unique_config_identifier)
            if skip_this_config is False:
                res[key] = data



    all_runs_by_unplaced = {}
    all_runs_by_pmc = {}

    # Sort all_runs by num_unplaced
    temp_dict = OrderedDict(sorted(res.items(), key = lambda x: (getitem(x[1], 'num_unplaced_int'))))
    count = 0
    for key, value in temp_dict.items():
        count += 1
        all_runs_by_unplaced.update({count: value})

        if count >= 6:
            break

    del temp_dict

    # Sort all_runs by total_pmc
    temp_dict = OrderedDict(sorted(res.items(), key = lambda x: (getitem(x[1], 'total_pmc'))))
    count = 0
    for key, value in temp_dict.items():
        count += 1
        all_runs_by_pmc.update({count: value})

        if count >= 6:
            break
         */


        List<FinalEventCollection> resultsList = allRuns.values().stream()
                .collect(Collectors.toList());
        return resultsList;
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
