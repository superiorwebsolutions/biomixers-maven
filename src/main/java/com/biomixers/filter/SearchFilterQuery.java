package com.biomixers.filter;

public class SearchFilterQuery {
    private int minAllowedPerRestaurant;
    private int maxAllowedPerRestaurant;
    private int numDaysOfAvailability;
    private int numFoodPreferences;
    private boolean randomizeResults;

    public SearchFilterQuery(){

    }


    public SearchFilterQuery(int minAllowedPerRestaurant, int maxAllowedPerRestaurant, int numDaysOfAvailability, int numFoodPreferences, boolean randomize_results) {
        super();
        this.minAllowedPerRestaurant = minAllowedPerRestaurant;
        this.maxAllowedPerRestaurant = maxAllowedPerRestaurant;
        this.numDaysOfAvailability = numDaysOfAvailability;
        this.numFoodPreferences = numFoodPreferences;
        this.randomizeResults = randomize_results;
    }

    public int getMinAllowedPerRestaurant() {
        return minAllowedPerRestaurant;
    }

    public void setMinAllowedPerRestaurant(int minAllowedPerRestaurant) {
        this.minAllowedPerRestaurant = minAllowedPerRestaurant;
    }

    public int getMaxAllowedPerRestaurant() {
        return maxAllowedPerRestaurant;
    }

    public void setMaxAllowedPerRestaurant(int maxAllowedPerRestaurant) {
        this.maxAllowedPerRestaurant = maxAllowedPerRestaurant;
    }

    public int getNumDaysOfAvailability() {
        return numDaysOfAvailability;
    }

    public void setNumDaysOfAvailability(int numDaysOfAvailability) {
        this.numDaysOfAvailability = numDaysOfAvailability;
    }

    public int getNumFoodPreferences() {
        return numFoodPreferences;
    }

    public void setNumFoodPreferences(int numFoodPreferences) {
        this.numFoodPreferences = numFoodPreferences;
    }

    public boolean isRandomizeResults() {
        return randomizeResults;
    }

    public void setRandomizeResults(boolean randomizeResults) {
        this.randomizeResults = randomizeResults;
    }


    @Override
    public String toString() {
        return "SearchFilterQuery{" +
                "minAllowedPerRestaurant=" + minAllowedPerRestaurant +
                ", maxAllowedPerRestaurant=" + maxAllowedPerRestaurant +
                ", numDaysOfAvailability=" + numDaysOfAvailability +
                ", numFoodPreferences=" + numFoodPreferences +
                ", randomizeResults=" + randomizeResults +
                '}';
    }
}
