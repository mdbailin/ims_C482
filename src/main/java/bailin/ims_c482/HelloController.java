package bailin.ims_c482;

import bailin.ims_c482.model.Inventory;
import bailin.ims_c482.model.Part;
import bailin.ims_c482.model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    // Parts Table
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInvLevelColumn;
    @FXML
    private TableColumn<Part, Double> partCostColumn;
    @FXML
    private TextField partSearchArea;

    @FXML
    public Button addPartButton;
    @FXML
    public Button modifyPartButton;
    @FXML
    public Button deletePartButton;
    @FXML
    public Button addProductButton;
    @FXML
    public Button modifyProductButton;
    @FXML
    public Button deleteProductButton;



    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productIDColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInvLevelColumn;
    @FXML
    private TableColumn<Product, Double> productCostColumn;
    @FXML
    private TextField productSearchArea;

    public static int partPrimaryKey = 103;
    public static int productPrimaryKey = 103;

    public static Part selectedPart = null;
    public static Product selectedProduct = null;
    private Parent scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partsTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partId"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partCost"));

        productsTableView.setItems((Inventory.getAllProducts()));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInvLevelColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("productCost"));


    }

    public void addpartbuttonpushed(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/bailin/ims_C482/addPart.fxml"));
        stage.setTitle("Add Part");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method checks to see if the user has selected a part,
     and then loads and displays the ModifyPart GUI if they have.
     If the user has not selected a part,
     they are warned to select a part before attempting to modify it.
     */
    public void onModifyPartButton(ActionEvent actionEvent) throws IOException {
        if (selectedPart == null) {
            Alert selectPartWarning = new Alert(Alert.AlertType.WARNING);
            selectPartWarning.setContentText("Please select a part to modify.");
            selectPartWarning.show();
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("modifyPart.fxml"));
            Stage stage = (Stage) addPartButton.getScene().getWindow();
            Scene scene = new Scene(root, 1580, 780);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This method calls setSelectedPart() when the user clicks on the PartsTableView, and it is not empty.
     */
    public void onPartsTableViewClick(MouseEvent mouseEvent) {
        if (!partsTableView.getSelectionModel().isEmpty()) {
            setSelectedPart();
        }
    }

    /** This method sets selectedProduct to null to ensure the user does not unintentionally delete or modify a product
     * after they return to the ProductsTableView. The method then sets selectedPart to the Part found in allParts that
     * matches what the user clicked on in the partsTableView.
     */
    public void setSelectedPart() {
        selectedProduct = null;
        selectedPart = partsTableView.getSelectionModel().getSelectedItem();
    }

    public void onProductsTableViewClick(MouseEvent mouseEvent) {
        if (!productsTableView.getSelectionModel().isEmpty()) {
            System.out.println("Products view clicked");
            setSelectedProduct();
        }
    }

    public void setSelectedProduct() {
        selectedPart = null;
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
    }

    public void onModifyProductButton (ActionEvent event) throws IOException{
        if (selectedProduct == null) {
            Alert selectPartWarning = new Alert(Alert.AlertType.WARNING);
            selectPartWarning.setContentText("Please select a product to modify.");
            selectPartWarning.show();
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("modifyProduct.fxml"));
            Stage stage = (Stage) addPartButton.getScene().getWindow();
            Scene scene = new Scene(root, 1180, 780);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void searchPartButton(ActionEvent event) {
        String term = partSearchArea.getText();
        ObservableList foundParts = Inventory.lookupPart(term);
        if(foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Match");
            alert.setHeaderText("Unable to locate a part name with: " + term);
            alert.showAndWait();
        } else {
            partsTableView.setItems(foundParts);
        }
    }

    public void searchProductButton(ActionEvent event) {
        String term = productSearchArea.getText();
        ObservableList foundProducts = Inventory.lookupProduct(term);
        if(foundProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Match");
            alert.setHeaderText("Unable to locate a product name with: " + term);
            alert.showAndWait();
        } else {
            productsTableView.setItems(foundProducts);
        }
    }

    public void onDeletePartButton(ActionEvent event) {
        if (partsTableView.getSelectionModel().isEmpty()){
            infoDialog("Warning!", "No Part Selected","Please choose part from the above list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this part?")){
            int selectedPart = partsTableView.getSelectionModel().getSelectedIndex();
            partsTableView.getItems().remove(selectedPart);
        }
    }

    public void onDeleteProductButton(ActionEvent event) {
        if (productsTableView.getSelectionModel().isEmpty()){
            infoDialog("Warning!", "No Product Selected","Please choose product from the above list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this product?")){
            int selectedPart = productsTableView.getSelectionModel().getSelectedIndex();
            productsTableView.getItems().remove(selectedPart);
        }
    }

    static boolean confirmDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        }
        else {
            return false;
        }
    }

    static void infoDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML public void addProductButtonPushed(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setTitle("Add Product");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML public void exitButton(ActionEvent event) throws IOException{
        confirmDialog("Exit", "Are you sure you would like to exit the program?");
        {
            System.exit(0);
        }
    }
}