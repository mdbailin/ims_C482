package bailin.ims_c482.model;

public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int partId, String name, double cost, int stock, int min, int max, String companyName) {
        super(partId, name, cost, stock, min, max);

        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
