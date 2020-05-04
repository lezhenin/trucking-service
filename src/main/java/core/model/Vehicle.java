package core.model;

import core.repository.RepositoryItem;

public class Vehicle extends RepositoryItem {

    private String model;

    private int maxCargoWeight;
    private int maxCargoHeight;
    private int maxCargoWidth;
    private int maxCargoLength;

    public Vehicle(String model, int maxCargoWeight, int maxCargoHeight, int maxCargoWidth, int maxCargoLength) {
        this.model = model;
        this.maxCargoWeight = maxCargoWeight;
        this.maxCargoHeight = maxCargoHeight;
        this.maxCargoWidth = maxCargoWidth;
        this.maxCargoLength = maxCargoLength;
    }

    public String getModel() {
        return model;
    }

    public int getMaxCargoWeight() {
        return maxCargoWeight;
    }

    public int getMaxCargoHeight() {
        return maxCargoHeight;
    }

    public int getMaxCargoWidth() {
        return maxCargoWidth;
    }

    public int getMaxCargoLength() {
        return maxCargoLength;
    }
}
