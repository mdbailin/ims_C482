package bailin.ims_c482.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    //+ addPart(newPart:Part):void
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    //+ getAllParts():ObservableList<Part>
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    //+ addProduct(newProduct:Product):void
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    //+ getAllProducts():ObservableList<Product>
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    //+ lookupPart(partId:int):Part
    public static ObservableList<Part> lookupPartID(int partId) {
        ObservableList <Part> allParts = getAllParts();
        FilteredList<Part> matchingParts = new FilteredList<>(allParts, i-> i.getPartId() == partId);
        if(matchingParts.size() > 0) {
            return matchingParts;
        }
        else {
            Alert notFound = new Alert(Alert.AlertType.INFORMATION);
            notFound.setContentText("The part with id " + partId + " was not found.");
            notFound.show();
            return allParts;
        }
    }

    //+ lookupProduct(productId:int):Product
    public static ObservableList<Product> lookupProductID(int productId) {
        ObservableList <Product> allProducts = getAllProducts();
        FilteredList <Product> matchingProducts = new FilteredList<>(allProducts, i-> i.getProductId() == productId);
        if(matchingProducts.size() > 0) {
            return matchingProducts;
        }
        else {
            Alert notFound = new Alert(Alert.AlertType.INFORMATION);
            notFound.setContentText("The product with id " + productId + " was not found.");
            notFound.show();
            return allProducts;
        }
    }

    //+ lookupPart(partName:String):ObservableList<Part>
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList <Part> allParts = getAllParts();
        FilteredList <Part> matchingParts = new FilteredList<>(allParts, i-> i.getName().toLowerCase().contains(partName.toLowerCase()));
        if(matchingParts.size() > 0) {
            return matchingParts;
        }
        else {
            Alert notFound = new Alert(Alert.AlertType.INFORMATION);
            notFound.setContentText("The part with name " + partName + " was not found.");
            notFound.show();
            return allParts;
        }
    }

    //+ lookupProduct(productName:String):ObservableList<Product>
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList <Product> allProducts = getAllProducts();
        FilteredList <Product> matchingProducts = new FilteredList<>(
                allProducts,
                i-> i.getName().toLowerCase().contains(productName.toLowerCase()));
        if(matchingProducts.size() > 0) {
            return matchingProducts;
        }
        else {
            Alert notFound = new Alert(Alert.AlertType.INFORMATION);
            notFound.setContentText("The product with name " + productName + " was not found.");
            notFound.show();
            return allProducts;
        }
    }

    //+ updatePart(index:int, selectedPart:Part):void
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    //+ updateProduct(index:int, newProduct:Product):void
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    //+ deletePart(selectedPart:Part):boolean
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    //+ deleteProduct(selectedProduct:Product):boolean
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }
}
