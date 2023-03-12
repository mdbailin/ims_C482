package bailin.ims_c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import bailin.ims_c482.model.Inventory;
import bailin.ims_c482.model.Product;
import bailin.ims_c482.model.InHouse;
import bailin.ims_c482.model.Outsourced;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //InHouse Parts initialized
        Inventory.addPart(new InHouse(95, "Super Mario World", 19.99, 20, 0, 100, 1021));
        Inventory.addPart(new InHouse(96, "Super Metroid", 19.99, 30, 0, 100, 1029));
        Inventory.addPart(new InHouse(97, "Tinstar", 19.99, 40, 0, 100, 1281));
        Inventory.addPart(new InHouse(98, "Kirby Super Star", 19.99, 60, 0, 100, 1923));

        //Outsourced Parts initialized
        Inventory.addPart(new Outsourced(99, "Sonic the Hedgehog", 10.99, 20, 0, 100, "Sega"));
        Inventory.addPart(new Outsourced(100, "Earthworm Jim", 1.99, 30, 0, 100, "Sega"));
        Inventory.addPart(new Outsourced(101, "Final Fantasy VII", 29.99, 40, 0, 100, "Squaresoft"));
        Inventory.addPart(new Outsourced(102, "Final Fantasy VIII", 1, 60, 0, 100, "Squaresoft"));


        //Products initialized
        Inventory.addProduct(new Product(1, 10, 1, 50, "SNES GamePak", 449.00));
        Inventory.addProduct(new Product(2, 8, 1, 50, "Sega GamePak", 259.00));
        Inventory.addProduct(new Product(3, 8, 1, 50, "Squaresoft GamePak", 159.99));

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bailin/ims_C482/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 780);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}