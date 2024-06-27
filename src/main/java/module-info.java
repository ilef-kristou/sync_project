module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.google.gson;
    requires java.net.http;
    requires java.persistence;
    requires jakarta.ws.rs;
    requires jersey.server;
    requires java.ws.rs;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}
