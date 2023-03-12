package bailin.ims_c482.model;

public class InHouse extends Part {
    private int machineID;

    public InHouse(int partId, String name, double cost, int stock, int min, int max, int machineID) {
        super(partId, name, cost, stock, min, max);

        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
