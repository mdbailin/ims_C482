package bailin.ims_c482;

import bailin.ims_c482.model.InHouse;
import bailin.ims_c482.model.Outsourced;
import bailin.ims_c482.model.Part;
import bailin.ims_c482.model.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class modifyPart implements Initializable {
    @FXML
    private RadioButton outsourcedRadio;

    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private Label switchLabel;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceCostField;
    @FXML
    private TextField minField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField switchField;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private String companyName;
    private int machineId;


    private Stage stage;
    private Object scene;
    private int partId;

    private InHouse inHousePart = null;
    /** This Outsourced Part is temporary storage for modifying the current Part.
     */
    private Outsourced outsourcedPart = null;

    /** This int holds the current index of the Part getting modified.
     */
    private int currentIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!(HelloController.selectedPart.getClass() == InHouse.class)) {
            outsourcedRadio.setSelected(true);
            outsourcedPart = (Outsourced) HelloController.selectedPart;
        }
        else {
            inHouseRadio.setSelected(true);
            inHousePart = (InHouse) HelloController.selectedPart;
        }
        radioLabelSet();
        partSetup();
    }

    public void radioLabelSet() {
        String setText;
        if(outsourcedRadio.isSelected()) {
            setText = "Company Name";
        } else {
            setText = "Machine ID";
        }
        this.switchLabel.setText(setText);
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
        if (inHouseRadio.isSelected()) {
            try{
                machineId = Integer.parseInt(switchField.getText());
            } catch (NumberFormatException e) {
                addPart.warningValidationCheck(5);
                return false;
            }
            if (!addPart.intCheck(switchField.getText())) {
                addPart.warningValidationCheck(5);
                return false;
            }
        }
        else if (outsourcedRadio.isSelected()) {
            companyName = switchField.getText();
            //no need for a try/catch block here since a null string is not an exception
            if (!addPart.stringCheck(switchField.getText()) || companyName == null) {
                addPart.warningValidationCheck(6);
                return false;
            }
        }
        return true;
    }

    public void onRadioSelect(ActionEvent actionEvent) {
        radioLabelSet();
    }

    /** This method first calls validateFields(), and if all the fields are valid,
     * the method saves each field input and updates the Part with a new inHouse Part (if
     * inHouse is selected) or a new Outsourced Part (if Outsourced is selected). Then the
     * user is returned to the main menu.
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        if (validateAllFields()) {
            currentIndex = Inventory.getAllParts().indexOf(HelloController.selectedPart);
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double price = Double.parseDouble(priceCostField.getText());
            int inventory = Integer.parseInt(invField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());
            if (inHouseRadio.isSelected()) {
                int machineId = Integer.parseInt(switchField.getText());
                InHouse modifiedPart = new InHouse(id, name, price, inventory, min, max, machineId);
                Inventory.updatePart(currentIndex, modifiedPart);
            }
            else {
                String companyName = switchField.getText();
                Outsourced modifiedPart = new Outsourced(id, name, price, inventory, min, max, companyName);
                Inventory.updatePart(currentIndex, modifiedPart);
            }
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

    /** This method loads the main.fxml resource, and then creates a new scene to show
     * with that resource. The current window is closed.
     */
    private void closeWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1280, 780);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /** This method first checks whether inHousePart or outsourcedPart is not null. Then,
     * the method populates each of the fields with data from the Part and changes the
     * typeLabel depending on whether the part is inHouse or Outsourced.
     */
    public void partSetup() {

        if (inHousePart != null) {
            idField.setText(String.valueOf(inHousePart.getPartId()));
            nameField.setText(String.valueOf(inHousePart.getName()));
            invField.setText(String.valueOf(inHousePart.getStock()));
            priceCostField.setText(String.valueOf(inHousePart.getPartCost()));
            maxField.setText(String.valueOf(inHousePart.getMax()));
            minField.setText(String.valueOf(inHousePart.getMin()));
            switchField.setText(String.valueOf(inHousePart.getMachineID()));
        }
        else if (outsourcedPart != null){
            idField.setText(String.valueOf(outsourcedPart.getPartId()));
            nameField.setText(String.valueOf(outsourcedPart.getName()));
            invField.setText(String.valueOf(outsourcedPart.getStock()));
            priceCostField.setText(String.valueOf(outsourcedPart.getPartCost()));
            maxField.setText(String.valueOf(outsourcedPart.getMax()));
            minField.setText(String.valueOf(outsourcedPart.getMin()));
            switchField.setText(String.valueOf(outsourcedPart.getCompanyName()));
        }
    }

}
