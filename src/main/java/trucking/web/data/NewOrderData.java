package trucking.web.data;

public class NewOrderData {

    private int cargoWeight;
    private int cargoHeight;
    private int cargoWidth;
    private int cargoLength;

    private String shippingAddress;
    private String loadingAddress;
    private String deadline;

    public NewOrderData() { }

    public NewOrderData(
            int cargoWeight,
            int cargoHeight,
            int cargoWidth,
            int cargoLength,
            String shippingAddress,
            String loadingAddress,
            String deadline
    ) {
        this.cargoWeight = cargoWeight;
        this.cargoHeight = cargoHeight;
        this.cargoWidth = cargoWidth;
        this.cargoLength = cargoLength;
        this.shippingAddress = shippingAddress;
        this.loadingAddress = loadingAddress;
        this.deadline = deadline;
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public int getCargoHeight() {
        return cargoHeight;
    }

    public int getCargoWidth() {
        return cargoWidth;
    }

    public int getCargoLength() {
        return cargoLength;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getLoadingAddress() {
        return loadingAddress;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setCargoWeight(Integer cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public void setCargoHeight(int cargoHeight) {
        this.cargoHeight = cargoHeight;
    }

    public void setCargoWidth(int cargoWidth) {
        this.cargoWidth = cargoWidth;
    }

    public void setCargoLength(int cargoLength) {
        this.cargoLength = cargoLength;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setLoadingAddress(String loadingAddress) {
        this.loadingAddress = loadingAddress;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
