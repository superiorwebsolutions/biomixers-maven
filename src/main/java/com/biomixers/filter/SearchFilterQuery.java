package com.biomixers.filter;

public class SearchFilterQuery {
    private int minAllowedPerRestaurant = 4;
    private int maxAllowedPerRestaurant = 16;
    private int numDaysOfAvailability = 4;
    private int numFoodPreferences = 3;
    private int maxNumberOfMembersMetAllowance = 100;
    private float percentageOfMembersMet = 0.25f;
    private boolean randomizeResults = false;

    public SearchFilterQuery(){

    }

    public int getMinAllowedPerRestaurant() {
        return minAllowedPerRestaurant;
    }

    public void setMinAllowedPerRestaurant(int minAllowedPerRestaurant) {
        if(minAllowedPerRestaurant % 1 != 0 || minAllowedPerRestaurant < 1 ){
            System.out.println(this.maxAllowedPerRestaurant);
            throw new IllegalArgumentException("minAllowedPerRestaurant must be a positive integer");
        }
        this.minAllowedPerRestaurant = minAllowedPerRestaurant;
    }

    public int getMaxAllowedPerRestaurant() {
        return maxAllowedPerRestaurant;
    }

    public void setMaxAllowedPerRestaurant(int maxAllowedPerRestaurant) {
        if(maxAllowedPerRestaurant % 1 != 0 || maxAllowedPerRestaurant < 2 ){
            throw new IllegalArgumentException("maxAllowedPerRestaurant must be a positive integer");
        }
        this.maxAllowedPerRestaurant = maxAllowedPerRestaurant;
    }

    public int getNumDaysOfAvailability() {
        return numDaysOfAvailability;
    }

    public void setNumDaysOfAvailability(int numDaysOfAvailability) {
        if(numDaysOfAvailability < 1 || numDaysOfAvailability > 5 ){
            throw new IllegalArgumentException("numDaysOfAvailability must be an integer between 1 and 5");
        }
        this.numDaysOfAvailability = numDaysOfAvailability;
    }

    public int getNumFoodPreferences() {
        return numFoodPreferences;
    }

    public void setNumFoodPreferences(int numFoodPreferences) {
        if(numDaysOfAvailability < 1 || numDaysOfAvailability > 5 ){
            throw new IllegalArgumentException("numFoodPreferences must be an integer between 1 and 5");
        }
        this.numFoodPreferences = numFoodPreferences;
    }

    public int getMaxNumberOfMembersMetAllowance() {
        return maxNumberOfMembersMetAllowance;
    }

    public void setMaxNumberOfMembersMetAllowance(int maxNumberOfMembersMetAllowance) {

        this.maxNumberOfMembersMetAllowance = maxNumberOfMembersMetAllowance;
    }

    public float getPercentageOfMembersMet() {
        return percentageOfMembersMet;
    }

    public void setPercentageOfMembersMet(float percentageOfMembersMet) {
        this.percentageOfMembersMet = percentageOfMembersMet;
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
                ", maxNumberOfMembersMetAllowance=" + maxNumberOfMembersMetAllowance +
                ", percentageOfMembersMet=" + percentageOfMembersMet +
                ", randomizeResults=" + randomizeResults +
                '}';
    }
}
