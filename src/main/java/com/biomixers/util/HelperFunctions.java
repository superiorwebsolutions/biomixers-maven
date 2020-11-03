package com.biomixers.util;

import com.biomixers.event.Event;
import com.biomixers.event.EventCollection;
import com.biomixers.event.EventMemberAttending;
import com.biomixers.event.FinalEventCollection;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class HelperFunctions {


    public static List<String> randomFoodPreferences(List<String> list, int size, long seed) {

        Random rand = new Random(seed);

        //List<String> sample = new ArrayList<>();
        List<String> food_options = new ArrayList<>();
        food_options.add("American");
        food_options.add("Italian");
        food_options.add("Mexican");
        food_options.add("Indian");
        food_options.add("Chinese");
        String a[] = new String[] { "A", "B", "C", "D" };

        // getting the list view of Array
        //List<String> sample = new ArrayList<String>(Arrays.asList(a));
        List<String> sample = new ArrayList<>(size);

        for (int sortedSampleIndices[] = new int[size], i = 0; i < size; i++) {
            int index = rand.nextInt(list.size() - i);

            int j = 0;
            for (; j < i && index >= sortedSampleIndices[j]; j++)
                index++;
            //sample.set(i, String.valueOf(list.get(index)));
            sample.add(list.get(index));

            for (; j <= i; j++) {
                int temp = sortedSampleIndices[j];
                sortedSampleIndices[j] = index;
                index = temp;
            }
        }

        return sample;
    }

    public static String[] randomAvailability(List<String> list, int size, long seed) {

        Random rand = new Random(seed);



        //List<String> sample = new ArrayList<>();
        String[] sample = new String[size];

        for (int sortedSampleIndices[] = new int[size], i = 0; i < size; i++) {
            int index = rand.nextInt(list.size() - i);

            int j = 0;
            for (; j < i && index >= sortedSampleIndices[j]; j++)
                index++;
            sample[i] = list.get(index);
            //sample.add(list.get(index));

            for (; j <= i; j++) {
                int temp = sortedSampleIndices[j];
                sortedSampleIndices[j] = index;
                index = temp;
            }
        }

        return sample;


    }

    public static List<Integer> randomSampleInt(List<Integer> list, int size, long seed) {

        Random rand = new Random(seed);

        List<Integer> sample = new ArrayList<>();


        int i = 0;
        for (int[] sortedSampleIndices = new int[size]; i < size; i++) {
            int index = rand.nextInt(list.size() - i);

            int j = 0;
            for (; j < i && index >= sortedSampleIndices[j]; j++)
                index++;
            sample.add(list.get(index));

            for (; j <= i; j++) {
                int temp = sortedSampleIndices[j];
                sortedSampleIndices[j] = index;
                index = temp;
            }
        }

        return sample;
    }

    public static <T>List<T> convertArrayToList(T[] array) {

        // Alternative method
        // Arrays.stream(array).boxed().collect(Collectors.toList());

        List<T> list = new ArrayList<T>(array.length);
        Collections.addAll(list, array);
        return list;
    }

    // THIS SHOULD RETURN ARRAY, NOT LIST
    public static <C, T extends C> List<T> convertListToArray(Class<C> componentType, List<T> list) {
        //@SuppressWarnings("unchecked")
        C[] array = (C[])Array.newInstance(componentType, list.size());
        return list;
    }

    public static int findMedian(int a[], int n)
    {
        // First we sort the array
        Arrays.sort(a);

        // check for even case
        if (n % 2 != 0)
            return (int)a[n / 2];

        return (int)(a[(n - 1) / 2] + a[n / 2]) / 2;
    }

    public static void sortByPmcValues(Event event) {


        // attempt to sort by pmc and then numactiveconfigs (didn't work)
        /*
        map.entrySet().stream().sorted(Comparator.comparing(entry -> (((EventMemberAttending)((Map.Entry)entry).getValue()).getPmc()))
                .thenComparing(entry -> (((EventMemberAttending)((Map.Entry)entry).getValue()).getNumActiveConfigs())))
                .collect(Collectors.toList());
        //System.out.println(map.get(1).hashCode());

        return map;
         */


        List list = new LinkedList(event.getMembersAttending().entrySet());
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
                return ((Comparable)((EventMemberAttending)((Map.Entry)o2).getValue()).getPmc())
                        .compareTo(((EventMemberAttending)((Map.Entry)o1).getValue()).getPmc());


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
        event.setMembersAttending(sortedHashMap);
        //return sortedHashMap;



    }

    public static void sortAllConfigsByCount(EventCollection eventCollection){

        List list = new LinkedList(eventCollection.getConfigTree().entrySet());
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

                return ((Comparable)((Event)((Map.Entry)o2).getValue()).getCount())
                        .compareTo(((Event)((Map.Entry)o1).getValue()).getCount());



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
        eventCollection.setConfigTree(sortedHashMap);

    }


    public static Event getEvent(EventCollection temp){
        return temp.getEventById(1);
    }








}
