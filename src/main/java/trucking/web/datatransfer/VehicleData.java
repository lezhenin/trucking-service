package trucking.web.datatransfer;

public class VehicleData {

    private final String model;

    private final int maxCargoWeight;
    private final int maxCargoHeight;
    private final int maxCargoWidth;
    private final int maxCargoLength;

    public VehicleData(String model, int maxCargoWeight, int maxCargoHeight, int maxCargoWidth, int maxCargoLength) {
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
