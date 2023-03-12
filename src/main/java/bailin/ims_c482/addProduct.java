package bailin.ims_c482;

import bailin.ims_c482.model.Part;
import bailin.ims_c482.model.Inventory;
import bailin.ims_c482.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addProduct implements Initializable {
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partsIDColumn;
    @FXML
    private TableColumn<Part, String> partsNameColumn;
    @FXML
    private TableColumn<Part, Integer> partsInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partsCostColumn;

    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Product, Integer> associatedPartsIDColumn;
    @FXML
    private TableColumn<Product, String> associatedPartsNameColumn;
    @FXML
    private TableColumn<Product, Integer> associatedPartsInventoryColumn;
    @FXML
    private TableColumn<Product, Double> associatedPartsCostColumn;

    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    @FXML
    private TextField nameField;
    @FXML
    private TextField priceCostField;
    @FXML
    private TextField invField;
    @FXML
    private TextField minField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField searchField;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> originalParts = FXCollections.observableArrayList();

    private Stage stage;
    private Object scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        originalParts = Inventory.getAllParts();
        partsTableView.setItems(Inventory.getAllParts());
        associatedPartsTableView.setItems(associatedParts);

        //Columns and Table for un-associated parts.
        partsIDColumn.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsCostColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));

        //Columns and Table for associated parts
        associatedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("partId"));
        associatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));

    }

    public void updatePartTable() {
        partsTableView.setItems(Inventory.getAllParts());
    }

    private void updateAssociatedPartTable() {
        associatedPartsTableView.setItems(associatedParts);
    }

    public static void incrementProductId() {
        HelloController.productPrimaryKey++;
    }

    /** This method searches through Parts after the user presses enter after typing in the searchField.
     In addition, if the searchField is left empty, then
     the contents of the TextField are checked to determine if they are an integer or not. If the contents are an
     integer, the inventoryTableView is set to the results of partById search. If the contents are not an integer, the
     inventoryTableView is set to the results of a part name search.
     */
    public void onInventoryPartsSearchTextField(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER || searchField.getText() == "") {
            if (addPart.intCheck(searchField.getText())) {
                int partInt = Integer.parseInt(searchField.getText());
                partsTableView.setItems(Inventory.lookupPartID(partInt));
            }
            else {
                partsTableView.setItems(Inventory.lookupPart(searchField.getText()));
            }
        }
    }

    @FXML void addPartToProduct(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            associatedParts.add(selectedPart);
            updatePartTable();
            updateAssociatedPartTable();
        }

        else {
            HelloController.infoDialog("Select a part","Select a part", "Select a part to add to the Product");
        }
    }
    @FXML
    void deletePartFromProduct(ActionEvent event) {
        Part selectedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            HelloController.confirmDialog("Deleting Parts","Are you sure you want to delete " + selectedPart.getName() + " from the Product?");
            associatedParts.remove(selectedPart);
            updatePartTable();
            updateAssociatedPartTable();
        }

        else {
            HelloController.infoDialog("No Selection","No Selection", "Please choose something to remove");
        }
    }
    /** This method saves validated data from the respective TextFields as long as inputs are validated.
     @return true if all fields are validated, false if any are not.
     */
    public boolean validateAllFields() {
        name = nameField.getText();
        if (!addPart.stringCheck(name)) {
            addPart.warningValidationCheck(0);
            return false;
        }
        try{
            price = Double.parseDouble(priceCostField.getText());
        } catch (NumberFormatException e) {
            addPart.warningValidationCheck(1);
            return false;
        }
        if (!addPart.doubleCheck(priceCostField.getText())) {
            addPart.warningValidationCheck(1);
            return false;
        }
        try{
            stock = Integer.parseInt(invField.getText());
        } catch (NumberFormatException e) {
            addPart.warningValidationCheck(2);
            return false;
        }
        if (!addPart.intCheck(invField.getText())) {
            addPart.warningValidationCheck(2);
            return false;
        }
        try{
            min = Integer.parseInt(minField.getText());
        } catch (NumberFormatException e) {
            addPart.warningValidationCheck(3);
            return false;
        }
        if (!addPart.intCheck(minField.getText())) {
            addPart.warningValidationCheck(3);
            return false;
        }
        try{
            max = Integer.parseInt(maxField.getText());
        } catch (NumberFormatException e) {
            addPart.warningValidationCheck(4);
            return false;
        }
        if (!addPart.intCheck(maxField.getText())) {
            addPart.warningValidationCheck(4);
            return false;
        }
        if (min > max) {
            addPart.warningValidationCheck(4);
            return false;
        }
        if (stock > max || stock < min) {
            addPart.warningValidationCheck(2);
            return false;
        }
        return true;
    }
    /** This method checks validateFields() and adds temp Product to Inventory. Then incrementProductId() is called and
     * the window is closed.
     @throws IOException
     */
    public void onSaveButton(ActionEvent event) throws IOException {
        if (validateAllFields()) {
            Product tempProduct = new Product();
            tempProduct.setProductID(HelloController.productPrimaryKey);
            tempProduct.setName(name);
            tempProduct.setProductCost(price);
            tempProduct.setStock(stock);
            tempProduct.setMin(min);
            tempProduct.setMax(max);
            Inventory.addProduct(tempProduct);
            incrementProductId();
            closeWindow(event);
        }
    }
    @FXML
    public void cancelAddProduct(ActionEvent event) throws IOException {
        if (HelloController.confirmDialog("Cancel?", "Are you sure?")) {
            closeWindow(event);
        }
    }

    /** This method loads the main.fxml resource, and then creates a new scene to show
     * with that resource. The current window is closed.
     @throws IOException
     */
    public void closeWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
}
