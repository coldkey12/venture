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

public class AdminController {
    @FXML
    private Button addButton;

    @FXML
    private Label adminUsernameLabel;

    @FXML
    private TextField aircraftIdField;

    @FXML
    private ListView<String> allFlightsList;

    @FXML
    private TextField arrivalIdField;

    @FXML
    private Label backLabel;

    @FXML
    private TextField businessPriceField;

    @FXML
    private Label citiesButton;

    @FXML
    private ListView<String> customersRequestsList;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField departureIdField;

    @FXML
    private TextField departureTimeField;

    @FXML
    private TextField economyPriceField;

    @FXML
    private Button killFlight;

    @FXML
    private Label planesButton;

    @FXML
    private Button reviveButton;


    @FXML
    void initialize(){

        adminUsernameLabel.setText(CurrentUser.username);

        planesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    planesButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloController.class.getResource("adminPlanes.fxml"));
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

        DBhandler dBhandler = new DBhandler();
        ArrayList<Flights> flights = dBhandler.allFlights();
        reloadListView();
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

            addButton.setOnAction(actionEvent -> {
                try {
                    if (!(aircraftIdField.getText().equals("") || departureIdField.getText().equals("") ||
                            arrivalIdField.getText().equals("") || departureTimeField.getText().equals("") ||
                            economyPriceField.getText().equals("") || businessPriceField.getText().equals(""))) {
                        dBhandler.addFlight(
                                Integer.parseInt(aircraftIdField.getText())
                                , Integer.parseInt(departureIdField.getText())
                                , Integer.parseInt(arrivalIdField.getText())
                                , departureTimeField.getText()
                                , Double.parseDouble(economyPriceField.getText())
                                , Double.parseDouble(businessPriceField.getText())
                        );
                        reloadListView();
                    }
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            });

            allFlightsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    int index = allFlightsList.getSelectionModel().getSelectedIndex();
                    ArrayList<Flights> flights1 = dBhandler.allFlights();
                    try{
                        AdminHandler.flight = flights.get(index);
                    }catch (Exception exception){
                        System.out.println(exception.getMessage());
                    }
                }
            });

            deleteButton.setOnAction(actionEvent -> {
                ArrayList<Flights> flights2 = dBhandler.allFlights();
                try{
                    for (int i = 0; i < flights2.size(); i++) {
                        if(flights2.get(i).getId() == AdminHandler.flight.getId()){
                            dBhandler.makeInactiveFlight(flights2.get(i).getId());
                            AdminHandler.flight = null;
                        }
                    }
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
                reloadListView();
            });

            killFlight.setOnAction(actionEvent -> {
                ArrayList<Flights> flights2 = dBhandler.allFlights();
                try{
                    for (int i = 0; i < flights2.size(); i++) {
                        if(flights2.get(i).getId() == AdminHandler.flight.getId()){
                            dBhandler.killFlight(flights2.get(i).getId());
                            AdminHandler.flight = null;
                        }
                    }
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
                reloadListView();
            });

            reviveButton.setOnAction(actionEvent -> {
                ArrayList<Flights> flights2 = dBhandler.allFlights();
                try{
                    for (int i = 0; i < flights2.size(); i++) {
                        if(flights2.get(i).getId() == AdminHandler.flight.getId()){
                            dBhandler.reviveFlight(flights2.get(i).getId());
                            AdminHandler.flight = null;
                        }
                    }
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
                reloadListView();
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
    }

    public void reloadListView(){
        DBhandler dBhandler = new DBhandler();
        allFlightsList.getItems().clear();
        ArrayList<Flights> flights = dBhandler.allFlights();
        for (int i = 0; i < flights.size(); i++) {
            if(flights.get(i).getStatus().equals("DEAD")){
                flights.remove(i);
            }
        }

        for (int i = 0; i < flights.size(); i++) {
            allFlightsList.getItems().add(dBhandler.getCitiesName(flights.get(i).getDeparture_city_id())
                    + " - " + dBhandler.getCitiesName(flights.get(i).getArrival_city_id()) + " at "
                    + flights.get(i).getDeparture_time() + " price for business :[" + flights.get(i).getBusiness_place_price()
                    + "] ; price for economy :[" + flights.get(i).getEconomy_place_price() + "] status:" + flights.get(i).getStatus());
        }
    }
}
