open module com.uygar {
    requires javafx.controls;
    requires javafx.fxml;
    requires http.request;
    requires org.xerial.sqlitejdbc;
    requires spring.core;
    requires spring.web;
    requires java.sql;
    requires spring.boot;
    requires spring.webmvc;
    requires spring.beans;
    requires spring.boot.starter.web;
    requires spring.boot.starter.json;
    requires spring.boot.starter.logging;
    requires spring.boot.starter.tomcat;
    requires spring.aop;
    requires spring.boot.autoconfigure;

    exports com.uygar;
    exports com.uygar.properties;
    exports com.uygar.controller;
}