package com.biomixers.event;

import com.biomixers.member.Member;
import com.biomixers.util.HelperFunctions;
import org.hibernate.annotations.Check;

import java.util.*;
import java.util.stream.Collectors;

public class EventCollection {

    List<Member> membersList = new ArrayList<>();
    Map<Integer, Event> configTree = new HashMap<>();

    public EventCollection(){

    }


    public EventCollection(List<Member> originalMembersList){
        List<Member> membersList = new ArrayList<>();

        // Clone original members list so it's not affected when new EventCollections are created
        for(Member member : originalMembersList){
            membersList.add(member.clone());
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


        //System.out.println(masterArray.toString());



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


                    //////System.out.println(masterTree.get(tempIdCount));
                    //////System.out.println();

                }


            }
        }


        //System.out.println(masterTree.toString());



        // TODO
        //masterArray.update_all_pmc_values()
        //masterArray.calculate_configs(max_active_configs)
        //masterArray.update_all_pmc_values()
        //masterArray.sort_all_configs_by_count(sort_decreasing=False)

        //final_array = masterArray.get_final_array()


        masterArray = null;

        this.configTree = masterTree;

    }

    // TODO:  FIGURE OUT HOW TO MAKE THIS SORT BY num_active_configs values
    // TODO:  has something to do with getting (Map.Entry) (o2)).getValue().getValue()
    // TODO:  test code block here:  https://leetcode.com/playground/new/empty
    /*
    public class HMapSortingByValues {
  public static void main(String[] args) {
      HashMap<Integer, HashMap> hmap = new HashMap<Integer, HashMap>();
      hmap.put(5, new HashMap<String, Integer>() {{
    put("num_active_configs", 3);
}});
      hmap.put(11, new HashMap<String, Integer>() {{
    put("num_active_configs", 21);
}});

      hmap.put(4, new HashMap<String, Integer>() {{
    put("num_active_configs", 6);
}});

      System.out.println("Before Sorting:");
    Set set = hmap.entrySet();
    Iterator iterator = set.iterator();
      while(iterator.hasNext()) {
        Map.Entry me = (Map.Entry)iterator.next();
        System.out.print(me.getKey() + ": ");
        System.out.println(me.getValue());
    }
    Map<Integer, HashMap> map = sortByValues(hmap);
      System.out.println("After Sorting:");
    Set set2 = map.entrySet();
    Iterator iterator2 = set2.iterator();
      while(iterator2.hasNext()) {
        Map.Entry me2 = (Map.Entry)iterator2.next();
        System.out.print(me2.getKey() + ": ");
        System.out.println(me2.getValue());
    }
}

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {


                // Sorts in ASCENDING order:  3, 6, 21
//                After Sorting:
//                5: {num_active_configs=3}
//                4: {num_active_configs=6}
//                11: {num_active_configs=21}
                 // TODO:  Change (HashMap) to Members object
                 // TODO:  Instead of casting here, change original HashMap instantiation to be nested (ie:  Map.Entry<String, HashMap<String>>)
                 return ((Comparable) ((HashMap)(((Map.Entry)o1).getValue())).get("num_active_configs"))
                        .compareTo(((HashMap)(((Map.Entry)o2).getValue())).get("num_active_configs"));


//                return ((Comparable) ((Map.Entry) (o1)).getValue())
//                        .compareTo(((Map.Entry) (o2)).getValue());

}
        });

                // Here I am copying the sorted list in HashMap
                // using LinkedHashMap to preserve the insertion order
                HashMap sortedHashMap = new LinkedHashMap();
                for (Iterator it = list.iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                sortedHashMap.put(entry.getKey(), entry.getValue());
                }
                return sortedHashMap;
                }
                }
     */

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

    public Event getEventById(Event event){
        return this.configTree.get(event.configId);
    }
    public Event getEventById(int configId){
        return this.configTree.get(configId);
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

            Map<Integer, EventMemberAttending> map = HelperFunctions.sortByValues(this.getEventById(event.getConfigId()).getMembersAttending());



            this.getEventById(event.getConfigId()).setMembersAttending((HashMap<Integer, EventMemberAttending>) map);


        }



    }

    public void calculateConfigs(int maxActiveConfigs){
        List<Integer> deleteTheseKeysFromDict = new ArrayList<>();

        int count = 0;

        for(Event event: this.getConfigTree().values() ){

            Integer configId = event.getConfigId();
            count += 1;

            HashMap<Integer, EventMemberAttending> membersAttending = event.getMembersAttending();
            int currentAttendingCount = membersAttending.size();

//            TODO:  change this to while loop, while:  pmc > gvs.GLOBALS['MAX_NumberOfMembersMetAlreadyAllowance']) or (current_attending_count > gvs.GLOBALS['MAX_NumberOfMembersAllowedPerRestaurant']
//            (sort members_attending by member.num_active_configs and pmc)
            for(EventMemberAttending eventMemberAttending : membersAttending.values()) {

                Member memberData = eventMemberAttending.getMemberData();

                int pmc = eventMemberAttending.getPmc();

                //if gvs.GLOBALS['debug']:
                //    print('current_attending_count: ', current_attending_count)

                //            Skip over any members who are on less than 2 total config_branches
                //            Since this if statement is before the "MAX_NumberOfMembersMetAlreadyAllowance" check,
                //                it's possible some event configs will have more than the max allowed.  this is handled in run_sorter() at the very end

                if (memberData.getNumActiveConfigs() <= maxActiveConfigs)
                    continue;

                // TODO:  set up global variables
                int MIN_NumberOfMembersAllowedPerRestaurant = 6;
                if(currentAttendingCount <= MIN_NumberOfMembersAllowedPerRestaurant){
                    // if DEBUG
                    //print('Current attending count reached MIN_NumberOfMembersAllowedPerRestaurant ({})'.format(gvs.GLOBALS['MIN_NumberOfMembersAllowedPerRestaurant']))
                    break;
                }

//                Check if member is still on any other configurations before deleting!
//                    Current number of members attending this config


                //TODO CONTINUE HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


            }


        }
    }
/*
    public String toString() {
        Map map = configTree;
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<Integer, Event>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Event> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        return sb.toString();

    }

 */

    @Override
    public String toString() {

        return "EventCollection{" +
                "configTree=" + configTree +
                '}';
    }
}


