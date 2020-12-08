package com.biomixers.event;

import com.biomixers.member.Member;
import com.biomixers.BiomixersApplication;
import com.biomixers.util.HelperFunctions;
import com.biomixers.util.Constants;
import javafx.util.Pair;

import java.util.*;


public class EventCollection {

    List<Member> membersList = new ArrayList<>();
    Map<Integer, Event> configTree = new HashMap<>();




    public EventCollection(){

    }


    public EventCollection(List<Member> originalMembersList){
        super();

        List<Member> membersList = new ArrayList<>();

        // Clone original members list so it's not affected when new EventCollections are created
        for(Member member : originalMembersList){
            Member tempMember = member.clone();
            membersList.add(tempMember);
        }

        this.membersList = membersList;

        // Generate nested HashMap for all possible event configurations
        HashMap<Integer, Event> masterTree = new HashMap<>();

        HashMap<String, HashMap<String, HashMap<String, HashMap<Integer, EventMemberAttending>>>> masterArray = new HashMap<>();

        for(Member member : membersList) {
            int userId = member.getUserId();

            List<String> foodPreferences = member.getFoodPreferences();

            Map<String, String[]> availability = member.getAvailability();

            // This was the old traverse deep nested tree format
            for(String restaurantKey : foodPreferences) {
                if(masterArray.get(restaurantKey) == null){
                    masterArray.put(restaurantKey, new HashMap());
                }

                for(Map.Entry<String, String[]> dayOfWeekEntry : availability.entrySet()) {
                    String dayOfWeekKey = dayOfWeekEntry.getKey();
                    String[] dayOfWeek = dayOfWeekEntry.getValue();

                    if(masterArray.get(restaurantKey).get(dayOfWeekKey) == null){
                        masterArray.get(restaurantKey).put(dayOfWeekKey, new HashMap());
                    }

                    for(String timeOfDayKey : dayOfWeek) {
                        // Uncomment to hide member data when printing output
                        // member = ''

                        member.numActiveConfigsPlusOne();

                        if(masterArray.get(restaurantKey).get(dayOfWeekKey).get(timeOfDayKey) == null){
                            masterArray.get(restaurantKey).get(dayOfWeekKey).put(timeOfDayKey, new HashMap());
                        }

                        masterArray.get(restaurantKey).get(dayOfWeekKey).get(timeOfDayKey).put(userId, new EventMemberAttending(userId, member, 0));

                    }
                }
            }
        }

        // Populate configTree HashMap of Events
        int idCount = 0;
        ////System.out.println(masterArray.toString());

        for(Map.Entry<String, HashMap<String, HashMap<String, HashMap<Integer, EventMemberAttending>>>> restaurantEntry : masterArray.entrySet()) {

            for (Map.Entry<String, HashMap<String, HashMap<Integer, EventMemberAttending>>> dayOfWeekEntry : restaurantEntry.getValue().entrySet()) {

                for (Map.Entry<String, HashMap<Integer, EventMemberAttending>> timeOfDayEntry : dayOfWeekEntry.getValue().entrySet()) {
                    idCount++;
                    int tempIdCount = idCount;
                    int memberCount = timeOfDayEntry.getValue().size();

                    masterTree.put(
                            tempIdCount, new Event(tempIdCount, 0, memberCount, timeOfDayEntry.getValue(), 0, restaurantEntry.getKey(), dayOfWeekEntry.getKey(), timeOfDayEntry.getKey())
                    );
                }
            }
        }

        masterArray = null;

        this.configTree = masterTree;



    }

    public List<Member> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<Member> membersList) {
        this.membersList = membersList;
    }

    public Map<Integer, Event> getConfigTree() {
        return configTree;
    }

    public void setConfigTree(HashMap<Integer, Event> configTree) {
        this.configTree = configTree;
    }

    public void removeEvent(int eventId){
        this.configTree.remove(eventId);
    }

    public Event getEventById(Event event){
        return this.configTree.get(event.getConfigId());
    }
    public Event getEventById(int configId){
        return this.configTree.get(configId);
    }
    public Set<Integer> getMembersAsIntegerSet(){
        Set<Integer> set = new HashSet<>();
        for(Member member : this.membersList){
            set.add(member.getUserId());
        }
        return set;
    }



    public void updateAllPmcValues(boolean variable){


        for(Event event: this.getConfigTree().values() ){
            int total_pmc = 0;

            List<Integer> active_configs_list = new ArrayList<>();

            Map<Integer, EventMemberAttending> members_attending = event.getMembersAttending();

            for(EventMemberAttending eventMemberAttending : members_attending.values()){

                Member member = eventMemberAttending.getMemberData();

                List<Integer> prev_members_met = member.getMembersMet();

                List<Integer> prev_members_met_intersection = new ArrayList<>(members_attending.keySet());
                prev_members_met_intersection.retainAll(prev_members_met);

                int prev_met_count = prev_members_met_intersection.size();
                if(prev_met_count != eventMemberAttending.getPmc()){
                    if(variable == true){
                        System.out.println(eventMemberAttending);
                    }
                }

                eventMemberAttending.setPmc(prev_met_count);

                total_pmc += prev_met_count;

                active_configs_list.add(member.getNumActiveConfigs());
            }

            if(members_attending.size() > 0){
                this.getEventById(event).setTotalPmc(total_pmc);

                // Uncomment if you want to add functionality to sort EventCollection by medianNumAciveConfigs
                //Collections.sort(active_configs_list);
                //this.getEventById(event).setMedianNumActiveConfigs(active_configs_list.get(active_configs_list.size() / 2));
            }

            // TODO:  Sort PMC and then num_active_configs

            //Map<Integer, EventMemberAttending> map = HelperFunctions.sortByPmcValues(this.getEventById(event.getConfigId()).getMembersAttending());

            //this.getEventById(event.getConfigId()).setMembersAttending((HashMap<Integer, EventMemberAttending>) map);
            HelperFunctions.sortByPmcValues(event);
        }
    }







    public void deleteMemberFromAllConfigs(int userId, int startingConfigId){
        int count = 0;

        for(Event event : this.configTree.values()){
            int configId = event.getConfigId();

            if(Constants.DEBUG)
                System.out.println("attempting - Remove member " + userId + " from event " + configId);


            if(count == 0){
                count += 1;
                if(configId != startingConfigId){
                    if(Constants.DEBUG){
                        System.out.println("starting_config_id: " + startingConfigId);
                        System.out.println("KEY != STARTING_CONFIG_ID");
                    }
                    // TODO:  uncomment this exit and see why this is happening
                    System.exit(0);
                }
                continue;
            }
            else {
                if (this.configTree.get(configId).getMembersAttending().keySet().contains(userId)) {
                    this.configTree.get(configId).countMinusOne();
                    this.configTree.get(configId).getMembersAttending().get(userId).getMemberData().numActiveConfigsMinusOne();
                    this.configTree.get(configId).removeMemberFromAttending(userId);
                    if(Constants.DEBUG)
                        System.out.println("deleteMemberFromAllConfigs - Remove member " + userId + " from event " + configId);
                }
                count += 1;
            }
        }
    }












    public void calculateConfigs(int maxActiveConfigs, int maxAllowedPerRestaurant){
        List<Pair> deleteTheseKeysFromDict = new ArrayList<>();

        int count = 0;

        for(Event event: this.getConfigTree().values() ){

            Integer configId = event.getConfigId();
            count += 1;

            Map<Integer, EventMemberAttending> membersAttending = event.getMembersAttending();
            int currentAttendingCount = membersAttending.size();

//            TODO:  change this to while loop, while:  pmc > gvs.GLOBALS['MAX_NumberOfMembersMetAlreadyAllowance']) or (current_attending_count > gvs.GLOBALS['MAX_NumberOfMembersAllowedPerRestaurant']
//            (sort members_attending by member.num_active_configs and pmc)
            for(EventMemberAttending eventMemberAttending : membersAttending.values()) {

                int userId = eventMemberAttending.getUserId();

                Member memberData = eventMemberAttending.getMemberData();

                int pmc = eventMemberAttending.getPmc();

                //if gvs.GLOBALS['debug']:
                //    print('current_attending_count: ', current_attending_count)

                //            Skip over any members who are on less than 2 total config_branches
                //            Since this if statement is before the "MAX_NumberOfMembersMetAlreadyAllowance" check,
                //                it's possible some event configs will have more than the max allowed.  this is handled in run_sorter() at the very end

                if (memberData.getNumActiveConfigs() <= maxActiveConfigs)
                    continue;



                if(currentAttendingCount <= BiomixersApplication.getSearchFilterQuery().getMinAllowedPerRestaurant()){
                    if(Constants.DEBUG)
                        System.out.println("Current attending count reached MIN_NumberOfMembersAllowedPerRestaurant ("+BiomixersApplication.getSearchFilterQuery().getMinAllowedPerRestaurant());
                    break;
                }

//                Check if member is still on any other configurations before deleting!
//                    Current number of members attending this config
                // TODO:  uncomment this if we want to use pmc as a limiting factor
                if(/*pmc > BiomixersApplication.getSearchFilterQuery().getMaxNumberOfMembersMetAllowance() || */currentAttendingCount > maxAllowedPerRestaurant){
                    // Deduct one from this member's num_active_configs
                    memberData.numActiveConfigsMinusOne();

                    for(EventMemberAttending m : membersAttending.values()){
                        Member mData = m.getMemberData();
                        int mUserId = m.getUserId();
                        List<Integer> prevMembersMet = mData.getMembersMet();

                        if(prevMembersMet.contains(userId)){
                            if(m.getPmc() == 0){
                                throw new IllegalArgumentException("PMC must be above zero.  config_id:"+configId + " userId: " + mUserId);
                            }

                            m.pmcMinusOne();
                        }
                    }

                    // Delete this member with highest pmc
                    if(Constants.DEBUG)
                        System.out.println("Deleted member with highest PMC:  " + userId);

                    // Determine which config_branches to delete from master config_tree, and create a list of keys so we can delete after the loop
                    deleteTheseKeysFromDict.add(new Pair<>(configId, userId));
                    currentAttendingCount -= 1;

                    event.setCount(currentAttendingCount);
                }
                else{
                    event.setCount(currentAttendingCount);
                    break;
                }
            }
        }

        // This code only executes once we reach acceptable global values for PMC and current_attending_count
        // Delete config_branches from master config_tree (decided in loop above)
        if(deleteTheseKeysFromDict.size() > 0){
            for(Pair elem : deleteTheseKeysFromDict){
                int cid = (int) elem.getKey();
                int uid = (int) elem.getValue();

                // Print config_id, user_id, and num_active configs (for each member deleted from all lists)
                Map<Integer, EventMemberAttending> membersAttending = this.getEventById(cid).getMembersAttending();

                Event event = this.getEventById(cid);

                event.removeMemberFromAttending(uid);

                if(Constants.DEBUG)
                    System.out.println(membersAttending.size() + "CalculateConfigs - Remove member " + uid + " from event " + cid);

                // If this Event has less than MIN_NumberOfMembersAllowedPerRestaurant, update numActiveConfigs and remove this Event
                if(membersAttending.size() <= BiomixersApplication.getSearchFilterQuery().getMinAllowedPerRestaurant()){
                    for(int userId : membersAttending.keySet()){
                        if(event.getMembersAttending().get(userId).getMemberData().getNumActiveConfigs() == 1){
                            if(Constants.DEBUG)
                                System.out.println("WARNING - User (" + userId + ") is no longer on any active Events after being deleted here");
                        }

                        event.getMembersAttending().get(userId).getMemberData().numActiveConfigsMinusOne();
                    }

                    if(Constants.DEBUG)
                        System.out.println("REMOVE THIS CONFIG - HAS LESS THAN MINIMUM ALLOWED PER RESTAURANT");
                    this.removeEvent(cid);
                }


            }
        }

    }





    public FinalEventCollection getFinalArray(){

        FinalEventCollection finalEventCollection = new FinalEventCollection();

        Map<Integer, Event> finalConfigTree = new HashMap<>();

        List<Integer> placedMemberList = new ArrayList<>();

        Iterator it = this.configTree.entrySet().iterator();

        Map.Entry i = (Map.Entry) it.next();
        int configId = (int) i.getKey();
        Event event = (Event) i.getValue();

        int count = 0;

        while(it.hasNext()){

            if(Constants.DEBUG)
                System.out.println("eventId: " + configId);

            // TODO:  NO LONGER NEEDED, DELETE THIS CODE
            /*
            if(count > configTreeLength - 2){
                break;
            }
             */

            Map<Integer, EventMemberAttending> membersAttending = event.getMembersAttending();

            boolean skipUpdates = false;
            if(membersAttending.size() == 0){
                skipUpdates = true;
                if(Constants.DEBUG)
                    System.out.println("skipUpdates - 0 members attending");
            }
            else {
                int numMembersAttending = membersAttending.size();

                // if members_attending is >= min number allowed, then delete member from all other configs
                if (numMembersAttending >= BiomixersApplication.getSearchFilterQuery().getMinAllowedPerRestaurant() && numMembersAttending <= BiomixersApplication.getSearchFilterQuery().getMaxAllowedPerRestaurant()) {
                    int totalPmc = 0;

                    Event newEvent = event.clone();

                    for (EventMemberAttending member : membersAttending.values()) {
                        totalPmc += member.getPmc();

                        int userId = member.getUserId();

                        this.deleteMemberFromAllConfigs(userId, configId);

                        placedMemberList.add(userId);
                    }

                    newEvent.setTotalPmc(totalPmc);

                    finalConfigTree.put(configId, newEvent);

                    if(Constants.DEBUG)
                        System.out.println("PLACED EVENT IN finalConfigTree: " + configId);

                }
                // else, if members_attending is less than min number allowed, subtract 1 from num_active_configs
                else {
                    for (EventMemberAttending eventMemberAttending : membersAttending.values()) {
                        int userId = eventMemberAttending.getUserId();
                        this.configTree.get(configId).getMembersAttending().get(userId).getMemberData().numActiveConfigsMinusOne();

                        skipUpdates = true;
                    }
                    if(Constants.DEBUG)
                        System.out.println("delete Event, attending is less than MIN_NumberOfMembersAllowedPerRestaurant");
                }

                if (skipUpdates == false) {
                    //this.updateAllPmcValues(true);

                    HelperFunctions.sortAllConfigsByCount(this);
                }
            }




            this.removeEvent(configId);

            it = this.configTree.entrySet().iterator();
            i = (Map.Entry) it.next();
            configId = (int) i.getKey();
            event = (Event) i.getValue();


            count += 1;

        }



        if(placedMemberList.size() == this.getMembersList().size()){
            if(Constants.DEBUG)
                System.out.println("ALL MEMBERS PLACED SUCCESSFULLY");
        }

        int totalPmcForConfig = 0;

        List<Integer> finalMemberList = new ArrayList<>();

        for(Event config : finalConfigTree.values()){
            totalPmcForConfig += config.getTotalPmc();

            for(EventMemberAttending eventMemberAttending : config.getMembersAttending().values()){
                int userId = eventMemberAttending.getUserId();
                finalMemberList.add(userId);
            }
        }

        if(Constants.DEBUG)
            System.out.println(finalMemberList.size() + " members placed of " + this.membersList.size());

        Set<Integer> unplacedMembersIntList = this.getMembersAsIntegerSet();
        unplacedMembersIntList.removeAll(finalMemberList);

        // Convert list of userIds to list of user fullNames
        List<Member> unplacedMembers = new ArrayList<>();
        for(Member member : this.membersList){
            int userId = member.getUserId();
            if(unplacedMembersIntList.contains(userId))
                unplacedMembers.add(member);
        }

        if(Constants.DEBUG)
            System.out.println("Unplaced:  " + unplacedMembersIntList.toString());

        finalEventCollection.setConfigTree(finalConfigTree);
        finalEventCollection.setMembersPlacedList(unplacedMembers);
        finalEventCollection.setHtmlPlaced(finalMemberList.size() + " members placed of " + this.membersList.size());
        finalEventCollection.setTotalPmc(totalPmcForConfig);
        finalEventCollection.setNumUnplacedInt(unplacedMembersIntList.size());
        finalEventCollection.setNumPlacedInt(finalMemberList.size());


        return finalEventCollection;

    }

    public static FinalEventCollection runSorter(List<Member> membersList, int maxActiveConfigs, int maxAllowedPerRestaurant){

        EventCollection eventCollection = new EventCollection(membersList);

        eventCollection.updateAllPmcValues(false);

        HelperFunctions.sortAllConfigsByCount(eventCollection);

        eventCollection.calculateConfigs(maxActiveConfigs, maxAllowedPerRestaurant);

        HelperFunctions.sortAllConfigsByCount(eventCollection);

        FinalEventCollection finalEventCollection = eventCollection.getFinalArray();

        return finalEventCollection;
    }



    @Override
    public String toString() {

        return "EventCollection{" +
                "configTree=" + configTree +
                '}';
    }
}


