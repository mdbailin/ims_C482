package bailin.ims_c482;

import bailin.ims_c482.model.InHouse;
import bailin.ims_c482.model.Outsourced;
import bailin.ims_c482.model.Part;
import bailin.ims_c482.model.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class addPart implements Initializable {
    @FXML
    private RadioButton outsourcedRadio;

    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private Label switchLabel;
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

    public void radioLabelSet() {
        String setText;
        if(outsourcedRadio.isSelected()) {
            setText = "Company Name";
        } else {
            setText = "Machine ID";
        }
        this.switchLabel.setText(setText);
    }

    @FXML public void onActionCancel(ActionEvent event) throws IOException {
        if(HelloController.confirmDialog("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /** This method generates a new Alert, and uses a switch statement
     * to set the text based on the field code entered.
     @param warningNumber int that correlates with the desired message.
     */
    public static void warningValidationCheck(int warningNumber) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        switch (warningNumber) {
            case 0:
                alert.setTitle("Invalid Name");
                alert.setHeaderText("Please enter a valid name.");
                alert.setContentText("The name must start with an alphabetic character (a-z).");
                break;
            case 1:
                alert.setTitle("Invalid Price");
                alert.setHeaderText("Please enter a valid price.");
                alert.setContentText("The price must be a number greater than 0.");
                break;
            case 2:
                alert.setTitle("Invalid Inventory");
                alert.setHeaderText("Please enter a valid inventory.");
                alert.setContentText("The inventory must be a number between the minimum and maximum.");
                break;
            case 3:
                alert.setTitle("Invalid Minimum");
                alert.setHeaderText("Please enter a valid minimum.");
                alert.setContentText("The minimum must be a number less than the maximum.");
                break;
            case 4:
                alert.setTitle("Invalid Maximum");
                alert.setHeaderText("Please enter a valid maximum.");
                alert.setContentText("The maximum must be a number greater than the minimum.");
                break;
            case 5:
                alert.setTitle("Invalid Machine ID");
                alert.setHeaderText("Please enter a valid machine ID.");
                alert.setContentText("The machine ID must be a number greater than 0.");
                break;
            case 6:
                alert.setTitle("Invalid Company Name");
                alert.setHeaderText("Please enter a valid company name.");
                alert.setContentText("The company name must begin with an alphabetic character (a-z).");
                break;
        }
        alert.showAndWait();
    }

    /** This method uses a regular expression to determine if the first character
     * of a string is an alphabetic character.
     * The method returns true if the String argument begins with an alphabetic character,
     * or returns false if the argument is null, an empty string, or begins with a non-alphabetic character.
     @param testString The String to be checked.
     @return true if checkMe begins with an alphabetic character, false if not.
     */
    public static boolean stringCheck(String testString) {
        if (testString == null || testString == "") {
            return false;
        }
        if (Pattern.compile("^[a-zA-Z]").matcher(testString).find()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean doubleCheck(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean intCheck(String input){
        try{
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** This method permits data to be saved only if all checks have passed.
     * This method also uses try/catch blocks to determine if any of the fields
     * have been left blank, and treats blank fields as if they were entered
     * incorrectly.
     @return true if all fields are validated, false if any are not.
     */
    public boolean validateAllFields() {
        name = nameField.getText();
        //no need for a try/catch block here since a null string is not an exception
        if (!stringCheck(name) || name == null) {
            warningValidationCheck(0);
            return false;
        }
        try{
            price = Double.parseDouble(priceCostField.getText());
        } catch (NumberFormatException e) {
            warningValidationCheck(1);
            return false;
        }
        if (!doubleCheck(priceCostField.getText())) {
            warningValidationCheck(1);
            return false;
        }
        try{
            stock = Integer.parseInt(invField.getText());
        } catch (NumberFormatException e) {
            warningValidationCheck(2);
            return false;
        }
        if (!intCheck(invField.getText())) {
            warningValidationCheck(2);
            return false;
        }
        try{
            min = Integer.parseInt(minField.getText());
        } catch (NumberFormatException e) {
            warningValidationCheck(3);
            return false;
        }
        if (!intCheck(minField.getText())) {
            warningValidationCheck(3);
            return false;
        }
        try{
            max = Integer.parseInt(maxField.getText());
        } catch (NumberFormatException e) {
            warningValidationCheck(4);
            return false;
        }
        if (!intCheck(maxField.getText())) {
            warningValidationCheck(4);
            return false;
        }
        if (min > max) {
            warningValidationCheck(4);
            return false;
        }
        if (stock > max || stock < min) {
            warningValidationCheck(2);
            return false;
        }
        if (inHouseRadio.isSelected()) {
            try{
                machineId = Integer.parseInt(switchField.getText());
            } catch (NumberFormatException e) {
                warningValidationCheck(5);
                return false;
            }
            if (!intCheck(switchField.getText())) {
                warningValidationCheck(5);
                return false;
            }
        }
        else if (outsourcedRadio.isSelected()) {
            companyName = switchField.getText();
            //no need for a try/catch block here since a null string is not an exception
            if (!stringCheck(switchField.getText()) || companyName == null) {
                warningValidationCheck(6);
                return false;
            }
        }
        return true;
    }

    /** This method increments partId.
     */
    public static void incrementPartId() {
        HelloController.partPrimaryKey++;
    }

    /** This method loads the main.fxml resource, and then creates a new scene to show
     * with that resource. The current window is closed.
     */
    private void closeWindow (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 780, 1280);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** This method checks if inHouse is selected, and calls validateAllFields() before adding a new part.
     * If the inHouse button is selected, an InHouse Part is added; otherwise, an Outsourced Part is added.
     After the Part is added to allParts, the partID is incremented and the window is closed.
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        boolean outSourced = outsourcedRadio.isSelected();
        int id = HelloController.partPrimaryKey;
        if (validateAllFields()) {
            Part newPart;
            if (outSourced) {
                newPart = new Outsourced(id, name, price, stock, min, max, companyName);
            }
            else {
                newPart = new InHouse(id, name, price, stock, min, max, machineId);
            }
            Inventory.addPart(newPart);
            incrementPartId();
            closeWindow(actionEvent);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
