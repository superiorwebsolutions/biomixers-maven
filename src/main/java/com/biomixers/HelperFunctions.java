package com.biomixers;

import java.lang.reflect.Array;
import java.util.*;

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




}
