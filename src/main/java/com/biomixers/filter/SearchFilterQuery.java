package com.biomixers.filter;

public class SearchFilterQuery {
    private int minAllowedPerRestaurant = 6;
    private int maxAllowedPerRestaurant = 14;
    private int numDaysOfAvailability = 4;
    private int numFoodPreferences = 3;
    private int maxNumberOfMembersMetAllowance = 100;
    private float percentageOfMembersMet = 0.25f;
    private boolean randomizeResults = false;

    public SearchFilterQuery(){

    }
//

    public SearchFilterQuery(int minAllowedPerRestaurant, int maxAllowedPerRestaurant, int numDaysOfAvailability, int numFoodPreferences, int maxNumberOfMembersMetAllowance, float percentageOfMembersMet, boolean randomizeResults) {
        super();
        this.minAllowedPerRestaurant = minAllowedPerRestaurant;
        this.maxAllowedPerRestaurant = maxAllowedPerRestaurant;
        this.numDaysOfAvailability = numDaysOfAvailability;
        this.numFoodPreferences = numFoodPreferences;
        this.percentageOfMembersMet = percentageOfMembersMet;
        this.randomizeResults = randomizeResults;
        this.maxNumberOfMembersMetAllowance = maxNumberOfMembersMetAllowance;
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
