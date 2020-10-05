package com.biomixers.event;

import com.biomixers.member.Member;

import java.util.*;

public class EventTree {

    List<Member> membersList = new ArrayList<>();
    TreeMap<Integer, EventBranch> configTree = new TreeMap<>();

    public EventTree(){

    }


// TODO:  FIGURE OUT HOW TO MAKE THIS SORT BY num_active_configs values
    // TODO:  has something to do with getting (Map.Entry) (o2)).getValue().getValue()
    // TODO:  test code block here:  https://leetcode.com/playground/new/empty
    /*
    public class HMapSortingByvalues {
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
//      hmap.put(77, "Y");
//      hmap.put(9, "P");
//      hmap.put(66, "Q");
//      hmap.put(0, "R");
      System.out.println("Before Sorting:");
    Set set = hmap.entrySet();
    Iterator iterator = set.iterator();
      while(iterator.hasNext()) {
        Map.Entry me = (Map.Entry)iterator.next();
        System.out.print(me.getKey() + ": ");
        System.out.println(me.getValue());
    }
    Map<Integer, String> map = sortByValues(hmap);
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
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
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

    public EventTree(List<Member> membersList, TreeMap<Integer, EventBranch> configTree) {
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

    public TreeMap<Integer, EventBranch> getConfigTree() {
        return configTree;
    }

    public void setConfigTree(TreeMap<Integer, EventBranch> configTree) {
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
