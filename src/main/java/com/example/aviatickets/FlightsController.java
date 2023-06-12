package com.example.aviatickets;

import db.DBhandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.Flights;

import java.util.ArrayList;

public class FlightsController {
    @FXML
    private Label alertLabel;

    @FXML
    private ListView<String> flightsList;

    @FXML
    private Label helpButton;

    @FXML
    private Label menuButton;

    @FXML
    private Label myticketsButton;

    @FXML
    void initialize() {
        DBhandler dBhandler = new DBhandler();
        ArrayList<Flights> flights = dBhandler.allFlights();
        for (int i = 0; i < flights.size(); i++) {
            if(flights.get(i).getStatus().equals("INACTIVE")){
                flights.remove(i);
            }
        }
        if(flights.size()!=0){
        for (int i = 0; i < flights.size(); i++) {
            flightsList.getItems().add(dBhandler.getCitiesName(flights.get(i).getDeparture_city_id())
                    + " - " + dBhandler.getCitiesName(flights.get(i).getArrival_city_id()) + " at "
                    + flights.get(i).getDeparture_time() + " price for business :[" + flights.get(i).getBusiness_place_price()
                    + "] ; price for economy :[" + flights.get(i).getEconomy_place_price() + "]");
        }
        } else {
            alertLabel.setText("No flights available");
            }
            menuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        menuButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(FlightsController.class.getResource("menu.fxml"));
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
            myticketsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        myticketsButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(FlightsController.class.getResource("mytickets.fxml"));
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
            helpButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        helpButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(FlightsController.class.getResource("help.fxml"));
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
        }
    }

