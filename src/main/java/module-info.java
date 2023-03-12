module bailin.ims_c482 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens bailin.ims_c482 to javafx.fxml;
    opens bailin.ims_c482.model to javafx.fxml;

    exports bailin.ims_c482;
    exports bailin.ims_c482.model;
}