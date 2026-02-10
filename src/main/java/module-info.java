module com.example.ecommerce {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.rmi;
    requires java.sql;
    requires mysql.connector.j;

    opens com.example.ecommerce.model to javafx.base;
    opens com.example.ecommerce.controller to javafx.fxml;
    opens com.example.ecommerce to javafx.fxml;
    exports com.example.ecommerce;
    exports com.example.ecommerce.controller;
    exports com.example.ecommerce.dao;
    exports com.example.ecommerce.model;
}