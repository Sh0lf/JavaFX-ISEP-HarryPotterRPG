module com.isep.hpah {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires java.desktop;

    opens com.isep.hpah to javafx.fxml;
    exports com.isep.hpah;
    exports com.isep.hpah.views.JavaFXexample;
    exports com.isep.hpah.views.GUI.controller;
    opens com.isep.hpah.views.GUI.controller to javafx.fxml;
    opens com.isep.hpah.views.JavaFXexample to javafx.fxml;
    exports com.isep.hpah.views.GUI;
    opens com.isep.hpah.views.GUI to javafx.fxml;
}