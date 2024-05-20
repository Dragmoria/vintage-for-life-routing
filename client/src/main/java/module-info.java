module com.vintageforlife.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.httpcomponents.httpclient;
    requires com.fasterxml.jackson.core;
    requires jakarta.validation;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpcore;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.web;

    opens com.vintageforlife.client.http to javafx.fxml;
    exports com.vintageforlife.client.http;
    exports com.vintageforlife.client.homepage;
    opens com.vintageforlife.client.homepage to javafx.fxml;
    exports com.vintageforlife.client.dto;
    opens com.vintageforlife.client.dto to javafx.fxml;
    exports com.vintageforlife.client;
    opens com.vintageforlife.client to javafx.fxml;
    exports com.vintageforlife.client.enums;
    opens com.vintageforlife.client.enums to javafx.fxml;
    exports com.vintageforlife.client.UserManagement;
    opens com.vintageforlife.client.UserManagement to javafx.fxml;




}
