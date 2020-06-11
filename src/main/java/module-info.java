module Project {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.rmi;
    requires controlsfx;
    opens view;
    opens view.gui;


}