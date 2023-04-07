module com.isep.hpah {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.isep.hpah to javafx.fxml;
    exports com.isep.hpah;
    exports com.isep.hpah.JavaFXexample;
    opens com.isep.hpah.JavaFXexample to javafx.fxml;
}