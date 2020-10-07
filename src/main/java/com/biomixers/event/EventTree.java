package com.biomixers.event;

import com.biomixers.member.Member;

import java.util.*;

public class EventTree {

    List<Member> membersList = new ArrayList<>();
    HashMap<Integer, EventBranch> configTree = new HashMap<>();

    public EventTree(){

    }


    public EventTree(List<Member> membersList) {
        this.membersList = membersList;

        HashMap<Integer, EventBranch> masterTree = new HashMap<>();

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

                        ////////member.num_active_configs += 1

                        if(masterArray.get(restaurantKey).get(dayOfWeekKey).get(timeOfDayKey) == null){
                            masterArray.get(restaurantKey).get(dayOfWeekKey).put(timeOfDayKey, new HashMap());
                        }

                        masterArray.get(restaurantKey).get(dayOfWeekKey).get(timeOfDayKey).put(userId, new EventMemberAttending(userId, member, 0));
//                        masterArray.put(
//                            restaurantKey, new HashMap(){{
//                                put(dayOfWeekKey, new HashMap(){{
//                                    // TODO:  do not use "new HashMap".  need to find a way to update existing hashmap
//                                    put(timeOfDayKey, new HashMap()/*
//                                    {{
//                                        put(userId, new EventMemberAttending(userId, member, 0));
//
//                                    }}
//                                    */
//                                    );
//                                }});
//                            }}
//                        );
                    }
                }
            }


        }

        //System.out.println(masterArray.toString());




        int idCount = 0;
        ////System.out.println(masterArray.toString());

        for(Map.Entry<String, HashMap<String, HashMap<String, HashMap<Integer, EventMemberAttending>>>> restaurantEntry : masterArray.entrySet()) {

            //System.out.println(restaurantEntry.getValue().entrySet());

            for (Map.Entry<String, HashMap<String, HashMap<Integer, EventMemberAttending>>> dayOfWeekEntry : restaurantEntry.getValue().entrySet()) {
                //System.out.println(dayOfWeekEntry.getValue());


                for (Map.Entry<String, HashMap<Integer, EventMemberAttending>> timeOfDayEntry : dayOfWeekEntry.getValue().entrySet()) {
                    idCount++;
                    int tempIdCount = idCount;
                    int memberCount = timeOfDayEntry.getValue().size();

                    masterTree.put(
                            tempIdCount, new EventBranch(tempIdCount, 0, memberCount, timeOfDayEntry.getValue(), 0, restaurantEntry.getKey(), dayOfWeekEntry.getKey(), timeOfDayEntry.getKey())


//                            tempIdCount, new HashMap<String, Object>(){{
//                                put("configId", tempIdCount);
//                                put("totalPmc", 0);
//                                put("count", memberCount);
//                                put("membersAttending", timeOfDayEntry.getValue());
//                                put("totalNumActiveConfigs", 0);
//                                put("foodType", restaurantEntry.getKey());
//                                put("dayOfWeek", dayOfWeekEntry.getKey());
//                                put("timeOfDay", timeOfDayEntry.getKey());
//                            }}


                    );
                    //System.out.println(masterTree.get(restaurantEntry.getKey()).get(dayOfWeekEntry.getKey()));
                    //System.out.println(masterArray.get(restaurantEntry.getKey()).get(dayOfWeekEntry.getKey()).get(timeOfDayEntry.getKey()));
//                    masterTree.get(restaurantEntry.getKey()).get(dayOfWeekEntry.getKey()).get(timeOfDayEntry.getKey()).put(
//                            idCount,
//                    );


                    System.out.println(masterTree.get(tempIdCount));
                    System.out.println();

                }


            }
        }


        //System.out.println(masterTree.toString());









        masterArray = null;
/*
        for i, member in active_members_list.items():
        user_id = member.user_id
            #print('userid:  ',user_id)
        food_preferences = member.get_food_preferences()

        member_availability = member.get_availability()


            # This was the old traverse deep nested tree format
        for restaurantkey in food_preferences:
        for dayofweekkey, dayofweek in member_availability.items():
        for timeofdaykey in dayofweek:

                        # TODO:  remove the next line
                        #member = ''
        masterArray[restaurantkey][dayofweekkey][timeofdaykey].update({user_id: {'user_id': user_id, 'member_data': member, 'pmc': 0}})
        member.num_active_configs += 1
        */


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

    public EventTree(List<Member> membersList, HashMap<Integer, EventBranch> configTree) {
        super();
        this.membersList = membersList;
        this.configTree = configTree;
    }

    public List<Member> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<Member> membersList) {
        this.membersList = membersList;
    }

    public HashMap<Integer, EventBranch> getConfigTree() {
        return configTree;
    }

    public void setConfigTree(HashMap<Integer, EventBranch> configTree) {
        this.configTree = configTree;
    }

    @Override
    public String toString() {
        return "EventTree{" +
                "membersList=" + membersList +
                ", configTree=" + configTree +
                '}';
    }
}
