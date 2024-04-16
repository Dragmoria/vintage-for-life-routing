module com.vintageforlife.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.vintageforlife.client to javafx.fxml;
    exports com.vintageforlife.client;
}