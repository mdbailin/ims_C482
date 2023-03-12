package bailin.ims_c482;

import bailin.ims_c482.model.Inventory;
import bailin.ims_c482.model.Part;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class modifyProduct implements Initializable {


    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIDAddColumn;
    @FXML
    private TableColumn<Part, String> partNameAddColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelAddColumn;
    @FXML
    private TableColumn<Part, Double> partPricePerUnitAddColumn;


    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Product, Integer> associatedPartsIDColumn;
    @FXML
    private TableColumn<Product, String> associatedPartsNameColumn;
    @FXML
    private TableColumn<Product, Integer> associatedPartsInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> associatedPartsPricePerUnitColumn;


    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceCostField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private TextField idField;
    @FXML
    private TextField searchField;
    @FXML
    private TextField switchField;

    private Part selectedAddPart = null;
    /** This is the Part that will be deleted from tempTableView.
     */
    private Part selectedDeletePart = null;

    /** This is the Product will be saved.
     */
    private Product tempProduct;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productId;

    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private int currentIndex;
    private RadioButton inHouseRadio;
    private RadioButton outsourcedRadio;
    private int machineId;
    /** This is the String companyName that stores the name of the Outsourced company
     * for a Part.
     */
    private String companyName;
    /** This inHouse Part is temporary storage for modifying the current Part.
     */

    private Stage stage;
    private Object scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentIndex = bailin.ims_c482.model.Inventory.getAllProducts().indexOf(HelloController.selectedProduct);
        tempProduct = HelloController.selectedProduct;
        productSetup();

        partTableView.setItems(Inventory.getAllParts());
        associatedPartsTableView.setItems(tempProduct.getAllAssociatedParts());
        // Set Inventory Part table columns
        partIDAddColumn.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partNameAddColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelAddColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitAddColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));

        // Set associated Part table columns
        associatedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("partId"));
        associatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));

    }
    /** This method checks if temp Product is null, and then populates the fields with temp Product's data.
     */
    private void productSetup() {
        if(tempProduct == null){
            return;
        }
        idField.setText(String.valueOf(tempProduct.getProductId()));
        nameField.setText(String.valueOf(tempProduct.getName()));
        invField.setText(String.valueOf(tempProduct.getStock()));
        priceCostField.setText(String.valueOf(tempProduct.getProductCost()));
        maxField.setText(String.valueOf(tempProduct.getMax()));
        minField.setText(String.valueOf(tempProduct.getMin()));
    }

    /** This method sets selectedAddPart to the clicked Part so long
     * as inventoryTableView is not null.
     * @throws NullPointerException
     */
    public void setSelectedAddPart() throws NullPointerException {
        if (partTableView != null) {
            selectedAddPart = partTableView.getSelectionModel().getSelectedItem();
        }
    }

    public void onPartTableViewClick(MouseEvent mouseEvent) {
        if (!partTableView.getSelectionModel().isEmpty()) {
            setSelectedAddPart();
        }
    }

    /** This method sets selectedDeletePart to the clicked Part so long
     * as tempTableView is not null.
     * @throws NullPointerException
     */
    public void setSelectedDeletePart() throws NullPointerException {
        if (associatedPartsTableView != null) {
            selectedDeletePart = associatedPartsTableView.getSelectionModel().getSelectedItem();
        }
    }

    public void onAssociatedPartTableViewClick(MouseEvent mouseEvent) {
        if (!associatedPartsTableView.getSelectionModel().isEmpty()) {
            setSelectedDeletePart();
        }
    }

    /** This method permits data to be saved only if all checks have passed.
     @return true if all fields are validated, false if any are not.
     */
    public boolean validateAllFields() {
        name = nameField.getText();
        //no need for a try/catch block here since a null string is not an exception
        if (!addPart.stringCheck(name) || name == null) {
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

    /** This method loads the main.fxml resource, and then creates a new scene to show
     * with that resource. The current window is closed.
     @throws IOException
     */
    public void closeWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1280, 780);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
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
                partTableView.setItems(Inventory.lookupPartID(partInt));
            }
            else {
                partTableView.setItems(Inventory.lookupPart(searchField.getText()));
            }
        }
    }

    /** This method checks if selectedAddPart is not null, and if it's not, adds selectedAddPart to temp Product and
     * appends the Part to tempTableView. If selectedAddPart is null, the user is alerted to select a Part.
     */

    public void onAddButton(ActionEvent actionEvent) {
        if (selectedAddPart == null) {
            Alert addPartWarning = new Alert(Alert.AlertType.WARNING);
            addPartWarning.setContentText("Please select an part to add.");
            addPartWarning.show();
            return;
        }
        tempProduct.addAssociatedPart(selectedAddPart);
        associatedPartsTableView.setItems(tempProduct.getAllAssociatedParts());
    }

    /** This method checks if selectedDeletePart is not null, and if it's not, alerts the user that they are about
     * to Delete a Part. If the user selects CANCEL or closes the window, selectedDeletePart is set to null in order
     * to prevent unintended behavior. If the user selects OK, the Part is deleted from temp Product, tempTableView
     * is reset, and selectedDeletePart is set to null. If selectedDeletePart was null when onDeleteButton was pressed,
     * the user is alerted that no Part was selected.
     */
    public void onDeleteButton(ActionEvent actionEvent) {
        if (selectedDeletePart == null) {
            Alert deleteAssociatedPartWarning = new Alert(Alert.AlertType.WARNING);
            deleteAssociatedPartWarning.setContentText("No part selected. Please select an associated part to remove.");
            deleteAssociatedPartWarning.show();
            return;
        }
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setContentText("Are you sure you want to remove " + selectedDeletePart.getName() + "?");
        Optional<ButtonType> result = confirmation.showAndWait();
        if(!result.isPresent() || result.get() == ButtonType.CANCEL) {
            selectedDeletePart = null;
        }
        else if(result.get() == ButtonType.OK) {
            tempProduct.deleteAssociatedPart(selectedDeletePart);
            associatedPartsTableView.setItems(tempProduct.getAllAssociatedParts());
            selectedDeletePart = null;
        }
    }

    /** This method checks validateFields() and adds temp Product to Inventory. Then incrementProductId() is called and
     * the window is closed.
     @throws IOException
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        if (validateAllFields()) {
            tempProduct.setProductID(HelloController.productPrimaryKey);
            tempProduct.setName(nameField.getText());
            tempProduct.setProductCost(Double.parseDouble(priceCostField.getText()));
            tempProduct.setStock(Integer.parseInt(invField.getText()));
            tempProduct.setMin(Integer.parseInt(minField.getText()));
            tempProduct.setMax(Integer.parseInt(maxField.getText()));
            Inventory.updateProduct(currentIndex, tempProduct);
            closeWindow(actionEvent);
        }
    }
    /** This method calls closeWindow() and returns the user to the main menu.
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        if(HelloController.confirmDialog("Cancel Modification", "Are you sure you would like to cancel?")){
            closeWindow(actionEvent);
        }
    }
    /** This method, on click, calls setSelectedAddPart().
     */
    public void onInventoryPartsAddClicked (MouseEvent mouseEvent) {
        setSelectedAddPart();
    }

    /** This method, on click, calls setSelectedDeletePart() in the AssociatedPartTableView.
     */
    public void onInventoryPartsDeleteClicked (MouseEvent mouseEvent) {
        setSelectedDeletePart();
    }
}
