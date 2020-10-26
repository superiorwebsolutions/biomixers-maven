package com.biomixers.event;

import com.biomixers.BiomixersApplication;
import com.biomixers.member.Member;
import com.biomixers.util.HelperFunctions;
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
            int userId = member.getUser_id();

            List<String> foodPreferences = member.getFood_preferences();

            Map<String, String[]> availability = member.getAvailability();


            //System.out.println(member.toString());

            //for(HashMap<String, HashMap<String, HashMap<String, HashMap<Integer, HashMap>>>>)

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
        return this.configTree.get(event.configId);
    }
    public Event getEventById(int configId){
        return this.configTree.get(configId);
    }
    public Set<Integer> getMembersAsIntegerSet(){
        Set<Integer> set = new HashSet<>();
        for(Member member : this.membersList){
            set.add(member.getUser_id());
        }
        return set;
    }



    public void updateAllPmcValues(){

        ////System.out.println(HelperFunctions.getEvent(this).getMembersAttending().get(new Integer(10)));

        for(Event event: this.getConfigTree().values() ){
            int total_pmc = 0;

            List<Integer> active_configs_list = new ArrayList<>();

            HashMap<Integer, EventMemberAttending> members_attending = event.getMembersAttending();

            for(EventMemberAttending eventMemberAttending : members_attending.values()){

                Member member = eventMemberAttending.getMemberData();

                List<Integer> prev_members_met = member.getMembersMet();

                List<Integer> prev_members_met_intersection = new ArrayList<>(members_attending.keySet());
                prev_members_met_intersection.retainAll(prev_members_met);

                int prev_met_count = prev_members_met_intersection.size();

                eventMemberAttending.setPmc(prev_met_count);

                total_pmc += prev_met_count;

                // TODO:  Change this to median active configs instead?  charge sorted alg too
                active_configs_list.add(member.getNumActiveConfigs());
            }

            if(members_attending.size() > 0){
                this.getEventById(event).setTotalPmc(total_pmc);

                Collections.sort(active_configs_list);
                this.getEventById(event).setMedianNumActiveConfigs(active_configs_list.get(active_configs_list.size() / 2));
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

            System.out.println("attempting - Remove member " + userId + " from event " + configId);


            if(count == 0){
                count += 1;
                if(configId != startingConfigId){
                    System.out.println("starting_config_id: " + startingConfigId);
                    System.out.println("KEY != STARTING_CONFIG_ID");
                    //System.exit(0);
                }
                continue;
            }
            else {


                if (this.configTree.get(configId).getMembersAttending().keySet().contains(userId)) {
                    this.configTree.get(configId).countMinusOne();
                    this.configTree.get(configId).getMembersAttending().get(userId).getMemberData().numActiveConfigsMinusOne();
                    this.configTree.get(configId).removeMemberFromAttending(userId);
                    System.out.println("deleteMemberFromAllConfigs - Remove member " + userId + " from event " + configId);
                }
                count += 1;
            }
        }
    }












    public void calculateConfigs(int maxActiveConfigs){
        List<Pair> deleteTheseKeysFromDict = new ArrayList<>();

        int count = 0;



        for(Event event: this.getConfigTree().values() ){

            Integer configId = event.getConfigId();
            count += 1;

            HashMap<Integer, EventMemberAttending> membersAttending = event.getMembersAttending();
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
                    // if DEBUG
                    //print('Current attending count reached MIN_NumberOfMembersAllowedPerRestaurant ({})'.format(gvs.GLOBALS['MIN_NumberOfMembersAllowedPerRestaurant']))
                    break;
                }

//                Check if member is still on any other configurations before deleting!
//                    Current number of members attending this config
                if(pmc > BiomixersApplication.getSearchFilterQuery().getMaxNumberOfMembersMetAllowance() || currentAttendingCount > BiomixersApplication.getSearchFilterQuery().getMaxAllowedPerRestaurant()){
                    // Deduct one from this member's num_active_configs
                    memberData.numActiveConfigsMinusOne();

                    // GET DEBUG STATEMENT FROM PYTHON


                    for(EventMemberAttending m : membersAttending.values()){
                        Member member = eventMemberAttending.getMemberData();
                        int mUserId = m.getUserId();
                        List<Integer> prevMembersMet = member.getMembersMet();

                        if(prevMembersMet.contains(mUserId)){
                            if(membersAttending.get(mUserId).getPmc() == 0){
                                // raise Exception('PMC must be above zero.  config_id:{}, user_id:{}'.format(config_id, uid))
                            }

                            membersAttending.get(mUserId).pmcMinusOne();
                        }
                    }

                    // Delete this member with highest pmc
                    // INSERT DEBUG MESSAGE

                    // Determine which config_branches to delete from master config_tree, and create a list of keys so we can delete after the loop
                    //                    delete_these_keys_from_dict.append(user_id)
                    deleteTheseKeysFromDict.add(new Pair<>(configId, userId));
                    currentAttendingCount -= 1;

                    event.setCount(currentAttendingCount);
                    // Skip the below code and continue this loop
                    //System.out.println(deleteTheseKeysFromDict.toString());

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
                HashMap<Integer, EventMemberAttending> membersAttending = this.getEventById(cid).getMembersAttending();

                Event event = this.getEventById(cid);

                event.removeMemberFromAttending(uid);
                System.out.println("CalculateConfigs - Remove member " + uid + " from event " + cid);

                // If this Event has less than MIN_NumberOfMembersAllowedPerRestaurant, update numActiveConfigs and remove this Event
                if(membersAttending.size() <= BiomixersApplication.getSearchFilterQuery().getMinAllowedPerRestaurant()){
                    for(int userId : membersAttending.keySet()){// TODO:  set up global variables
                        if(event.getMembersAttending().get(userId).getMemberData().getNumActiveConfigs() == 1){
                            System.out.println("WARNING - User (" + userId + ") is no longer on any active Events after being deleted here");
                        }

                        event.getMembersAttending().get(userId).getMemberData().numActiveConfigsMinusOne();
                    }

                    System.out.println("REMOVE THIS CONFIG - HAS LESS THAN MINIMUM ALLOWED PER RESTAURANT");
                    this.removeEvent(cid);
                }


            }
        }

    }





    public FinalEventCollection getFinalArray(){


        EventCollection checkpoint = this;

        FinalEventCollection finalEventCollection = new FinalEventCollection();

        Map<Integer, Event> finalConfigTree = new HashMap<>();

        List<Integer> placedMemberList = new ArrayList<>();

        int configTreeLength = this.configTree.size();

        Iterator it = this.configTree.entrySet().iterator();

        Map.Entry i = (Map.Entry) it.next();
        int configId = (int) i.getKey();
        Event event = (Event) i.getValue();

        //System.out.println("config_tree_size:  " + this.configTree.size());

        int count = 0;

        while(it.hasNext()){




            System.out.println("eventId: " + configId);

            if(count > configTreeLength - 2){
                break;
            }


            HashMap<Integer, EventMemberAttending> membersAttending = event.getMembersAttending();

            boolean skipUpdates = false;
            if(membersAttending.size() == 0){
                skipUpdates = true;
                System.out.println("skipUpdates - 0 members attending");
            }
            else {

                // if members_attending is >= min number allowed, then delete member from all other configs
                if (membersAttending.size() >= BiomixersApplication.getSearchFilterQuery().getMinAllowedPerRestaurant()) {
                    Event newEvent = event.clone();
                    finalConfigTree.put(configId, newEvent);
                    System.out.println("PLACED EVENT IN finalConfigTree: " + configId);

                    for (int userId : membersAttending.keySet()) {
                        this.deleteMemberFromAllConfigs(userId, configId);
                        placedMemberList.add(userId);
                    }
                }
                // else, if members_attending is less than min number allowed, subtract 1 from num_active_configs
                else {
                    for (EventMemberAttending eventMemberAttending : membersAttending.values()) {
                        int userId = eventMemberAttending.getUserId();
                        this.configTree.get(configId).getMembersAttending().get(userId).getMemberData().numActiveConfigsMinusOne();

                        skipUpdates = true;
                    }
                    System.out.println("delete Event, attending is less than MIN_NumberOfMembersAllowedPerRestaurant");
                }

                if (skipUpdates == false) {
                    this.updateAllPmcValues();

                    HelperFunctions.sortAllConfigsByCount(this, false);
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

        System.out.println(finalMemberList.size() + " members placed of " + this.membersList.size());

        Set<Integer> unplacedMembers = this.getMembersAsIntegerSet();
        unplacedMembers.removeAll(finalMemberList);

        System.out.println("Unplaced:  " + unplacedMembers.toString());

        finalEventCollection.setConfigTree(finalConfigTree);
        finalEventCollection.setHtmlNumUnplaced("Unplaced:  " + unplacedMembers.toString());
        finalEventCollection.setHtmlPlaced(finalMemberList.size() + " members placed of " + this.membersList.size());
        finalEventCollection.setTotalPmc(totalPmcForConfig);
        finalEventCollection.setNumUnplacedInt(unplacedMembers.size());

        return finalEventCollection;

    }

    public static FinalEventCollection runSorter(List<Member> membersList, int maxActiveConfigs){

        EventCollection eventCollection = new EventCollection(membersList);

        eventCollection.updateAllPmcValues();

        HelperFunctions.sortAllConfigsByCount(eventCollection, false);

        eventCollection.calculateConfigs(maxActiveConfigs);

        eventCollection.updateAllPmcValues();

        HelperFunctions.sortAllConfigsByCount(eventCollection, false);

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


