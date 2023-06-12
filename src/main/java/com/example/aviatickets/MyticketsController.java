package com.example.aviatickets;

import db.DBhandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.CurrentUser;
import objects.Tickets;

import java.util.ArrayList;

public class MyticketsController {
    @FXML
    private Label alertLabel;

    @FXML
    private Label findLabel;

    @FXML
    private Label flightsButton;

    @FXML
    private ListView<String> flightsList;

    @FXML
    private Label helpButton;

    @FXML
    private Label menuButton;

    @FXML
    private TextField passportIdField;

    @FXML
    void initialize(){
        DBhandler dBhandler = new DBhandler();
        findLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                flightsList.getItems().clear();
                if(!passportIdField.getText().equals("")){
                    CurrentUser.passport_number = passportIdField.getText();
                    ArrayList<Tickets> myTickets = dBhandler.AllMyTickets();
                    for (int i = 0; i < myTickets.size(); i++) {
                        flightsList.getItems().add("Passport number: " + myTickets.get(i).getPassport_number() + " ; ticket class: " +
                                myTickets.get(i).getTicket_type() + "; from " +
                                dBhandler.getCitiesName(
                                        dBhandler.flightById(myTickets.get(i).getFlight_id()).getDeparture_city_id()
                                ) + " to " +
                                dBhandler.getCitiesName(
                                        dBhandler.flightById(myTickets.get(i).getFlight_id()).getArrival_city_id()
                                ));
                    }
                }
            }
        });

        menuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    menuButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MyticketsController.class.getResource("menu.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            }
        });
        flightsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    flightsButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MyticketsController.class.getResource("flights.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            }
        });
        helpButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    helpButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MyticketsController.class.getResource("help.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }catch (Exception exception){
                    System.out.println(exception.getStackTrace());
                }
            }
        });
    }
}
