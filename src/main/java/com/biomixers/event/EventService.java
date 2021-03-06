package com.biomixers.event;

import com.biomixers.BiomixersApplication;
import com.biomixers.filter.SearchFilterQuery;
import com.biomixers.member.Member;
import com.biomixers.member.MemberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private MemberController memberController;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMemberAttendingRepository eventMemberAttendingRepository;

    @Autowired
    private FinalEventCollectionRepository finalEventCollectionRepository;


    public List<FinalEventCollection> getFinalEventCollection() throws Exception {

        // If SearchFilterQuery has been changed, then refresh generated sample data
        if(!BiomixersApplication.getSearchFilterQuery().equals(new SearchFilterQuery())){
            memberController.generateSampleData();
        }


        List<Member> membersList = memberController.getAllMembers();

        HashMap<Integer, FinalEventCollection> allRuns = new HashMap<>();

        int membersMaxMinAllowedRange = BiomixersApplication.getSearchFilterQuery().getMaxAllowedPerRestaurant() - BiomixersApplication.getSearchFilterQuery().getMinAllowedPerRestaurant();

        //int numSteps = Math.round(maxRuns / membersMaxMinAllowedRange);
        int numSteps = 20;

        int maxRuns = numSteps * membersMaxMinAllowedRange;

        if(numSteps == 0)
            numSteps = 1;

        int descCount = 0;

        int maxNumberOfMembersAllowedPerRestaurant = BiomixersApplication.getSearchFilterQuery().getMaxAllowedPerRestaurant();

        int maxActiveConfigs;

        for(int i = 1; i < maxRuns + 1; i++){
            maxActiveConfigs = numSteps - descCount;


            FinalEventCollection finalEventCollection = EventCollection.runSorter(membersList, maxActiveConfigs, maxNumberOfMembersAllowedPerRestaurant);

            finalEventCollection.setMaxActiveConfigs(maxActiveConfigs);
            finalEventCollection.setMaxMembersAllowedPerRestaurant(maxNumberOfMembersAllowedPerRestaurant);

            if(membersList.size() > maxNumberOfMembersAllowedPerRestaurant) {
                allRuns.put(finalEventCollection.hashCode(), finalEventCollection);

                descCount += 1;

                if (((float) i % numSteps) == 0.0) {
                    maxNumberOfMembersAllowedPerRestaurant -= 1;
                    descCount = 0;
                }
            }


            if(i == 1){
//                Event event = finalEventCollection.getConfigTree().get(16);
                //System.out.println(event.getMembersAttending());

                //eventRepository.save(event);
//                System.out.println(finalEventCollection);
//                finalEventCollectionRepository.save(finalEventCollection);

//                System.out.println(eventMemberAttendingRepository.findById(95).get());
            }

            // TODO:  slowly increase ['MAX_NumberOfMembersMetAlreadyAllowance'] (maybe?)

        }



        //System.out.println(allRuns.toString());

        // OLD
        // List<FinalEventCollection> resultsList = allRuns.values().stream().collect(Collectors.toList());
        List<FinalEventCollection> resultsList = new ArrayList<>(allRuns.values());

        // Sort resultsList of FinalEventCollections by number of members placed
        resultsList.sort(Comparator.comparing(FinalEventCollection::getNumPlacedInt).reversed());

        return resultsList;
    }
}
