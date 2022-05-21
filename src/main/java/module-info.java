open module com.uygar {
    requires javafx.controls;
    requires javafx.fxml;
    requires http.request;
    requires mysql.connector.java;
    requires spring.core;
    requires spring.context;
    requires spring.web;
    requires java.sql;
    requires spring.boot;
    requires spring.webmvc;
    requires spring.beans;
    requires spring.boot.starter.web;
    requires spring.boot.starter.json;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.module.paramnames;
    requires spring.boot.starter.logging;
    requires spring.boot.starter.tomcat;
    requires spring.aop;
    requires spring.boot.autoconfigure;

    exports com.uygar;
    exports com.uygar.server;
    exports com.uygar.properties;
    exports com.uygar.model.observable;
    exports com.uygar.controller;
}