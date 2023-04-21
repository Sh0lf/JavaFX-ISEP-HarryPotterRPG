module com.isep.hpah {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;

    opens com.isep.hpah to javafx.fxml;
    exports com.isep.hpah;
    exports com.isep.hpah.views.JavaFXexample;
    opens com.isep.hpah.views.JavaFXexample to javafx.fxml;
}