package bailin.ims_c482.model;

public abstract class Part {
    private int partId;
    private String name;
    private double cost;
    private int stock;
    private int min;
    private int max;

    public Part(int partId, String name, double cost, int stock, int min, int max) {
        this.partId = partId;
        this.name = name;
        this.cost = cost;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //Getters
    public int getPartId() {
        return partId;
    }

    public int getStock() {
        return stock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getName() {
        return name;
    }

    public double getPartCost() {
        return cost;
    }


    //Setters
    public void setPartId(int id) {
        this.partId = id;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPartCost(double cost) {
        this.cost = cost;
    }
}
