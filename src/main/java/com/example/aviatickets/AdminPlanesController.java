package com.example.aviatickets;

import db.DBhandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.*;

import java.util.ArrayList;

public class AdminPlanesController {
    @FXML
    private ListView<String> CrewRequestsList;

    @FXML
    private Button addButton;

    @FXML
    private Label adminUsernameLabel;

    @FXML
    private TextField aircraftNameField;

    @FXML
    private ListView<String> allPlanesList;

    @FXML
    private Label backLabel;

    @FXML
    private TextField businessCapacityField;

    @FXML
    private Label citiesButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField economyCapacityField;

    @FXML
    private Label flightsButton;

    @FXML
    private Button killPlane;

    @FXML
    private TextField modelField;

    @FXML
    private Button reviveButton;

    @FXML
    void initialize(){
        reloadListView();

        adminUsernameLabel.setText(CurrentUser.username);
        DBhandler dBhandler = new DBhandler();

        backLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    backLabel.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloController.class.getResource("menu.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });

        flightsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    flightsButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloController.class.getResource("admin.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            }
        });

        citiesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    citiesButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloController.class.getResource("adminCities.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            }
        });

        allPlanesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = allPlanesList.getSelectionModel().getSelectedIndex();
                ArrayList<Aircrafts> aircrafts = dBhandler.allAircrafts();
                try{
                    AdminHandler.plane = aircrafts.get(index);
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            }
        });

        deleteButton.setOnAction(actionEvent -> {
            ArrayList<Aircrafts> planes2 = dBhandler.allAircrafts();
            try{
                for (int i = 0; i < planes2.size(); i++) {
                    if(planes2.get(i).getId() == AdminHandler.plane.getId()){
                        dBhandler.inactivePlane(planes2.get(i).getId());
                        AdminHandler.plane = null;
                    }
                }
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
            reloadListView();
        });

        killPlane.setOnAction(actionEvent -> {
            ArrayList<Aircrafts> planes2 = dBhandler.allAircrafts();
            try{
                for (int i = 0; i < planes2.size(); i++) {
                    if(planes2.get(i).getId() == AdminHandler.plane.getId()){
                        dBhandler.killPlane(planes2.get(i).getId());
                        AdminHandler.plane = null;
                    }
                }
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
            reloadListView();
        });

        reviveButton.setOnAction(actionEvent -> {
            ArrayList<Aircrafts> planes2 = dBhandler.allAircrafts();
            try{
                for (int i = 0; i < planes2.size(); i++) {
                    if(planes2.get(i).getId() == AdminHandler.plane.getId()){
                        dBhandler.revivePlane(planes2.get(i).getId());
                        AdminHandler.plane = null;
                    }
                }
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
            reloadListView();
        });

        addButton.setOnAction(actionEvent -> {
            try{
                if(!(aircraftNameField.getText().equals("") || modelField.getText().equals("") ||
                        economyCapacityField.getText().equals("") || businessCapacityField.getText().equals(""))){
                    dBhandler.addAircraft(aircraftNameField.getText(),modelField.getText(),
                            Integer.parseInt(businessCapacityField.getText()),Integer.parseInt(economyCapacityField.getText()));
                    reloadListView();
                }
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });

    }
    public void reloadListView(){
        DBhandler dBhandler = new DBhandler();
        allPlanesList.getItems().clear();
        ArrayList<Aircrafts> aircrafts = dBhandler.allAircrafts();
        for (int i = 0; i < aircrafts.size(); i++) {
            if(aircrafts.get(i).getStatus().equals("DEAD")){
                aircrafts.remove(i);
            }
        }

        for (int i = 0; i < aircrafts.size(); i++) {
            allPlanesList.getItems().add(aircrafts.get(i).getName() + " - " + aircrafts.get(i).getModel()
                    + " ; economy capacity[" + aircrafts.get(i).getEconomy_class_capacity() + "] ; buisness capacity["
                    + aircrafts.get(i).getBusiness_class_capacity() + "]; status: " + aircrafts.get(i).getStatus());
        }
    }

}
