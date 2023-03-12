package bailin.ims_c482.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productId;
    private int stock;
    private int min;

    private int max;
    private String name;
    private double productCost;

    public Product(int productId, int stock, int min, int max, String name, double productPrice) {
        this.productId = productId;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.name = name;
        this.productCost = productPrice;
    }

    public Product() {
        Product product = new Product(0, 0, 0, 0, null, 0.00);
    }

    public int getProductId() {
        return productId;
    }

    public int getStock() {
        return stock;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public String getName() {
        return name;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductID(int productID) {
        this.productId = productID;
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

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }

    public void deleteAssociatedPart(Part part){this.associatedParts.remove(part);}

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
