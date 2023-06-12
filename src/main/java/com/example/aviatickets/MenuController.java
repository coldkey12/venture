package com.example.aviatickets;

import db.DBhandler;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.Aircrafts;
import objects.CurrentUser;
import objects.Flights;
import objects.TicketTransactionHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuController {
    @FXML
    private Label adminLabel;

    @FXML
    private Label alertLabel;

    @FXML
    private Button buyticketButton;

    @FXML
    private TextArea flightDesc;

    @FXML
    private Label flightsButton;

    @FXML
    private ListView<String> flightsList;

    @FXML
    private TextField fromField;

    @FXML
    private Label helpButton;

    @FXML
    private Label logoutLabel;

    @FXML
    private Label myticketsButton;

    @FXML
    private Label searchTIcketsLabel;

    @FXML
    private TextField seatclassField;

    @FXML
    private Label ticketsManagmentLabel;

    @FXML
    private TextField timeField;

    @FXML
    private TextField toField;



    @FXML
    void initialize() throws IOException {
        DBhandler dBhandler = new DBhandler();
//        Socket socket = new Socket("127.0.0.1",8000);
//        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        if(CurrentUser.role==2){
            ticketsManagmentLabel.setText("Tickets management panel");
        } else if (CurrentUser.role==3) {
            adminLabel.setText("Admin management panel");
        }

        flightsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    flightsButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MenuController.class.getResource("flights.fxml"));
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
        myticketsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    myticketsButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MenuController.class.getResource("mytickets.fxml"));
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
                    loader.setLocation(MenuController.class.getResource("help.fxml"));
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
        // ACTIONS WITHIN THE PAGE DOWN BELLOW
        searchTIcketsLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Flights> flights = new ArrayList<>();
                boolean isEmpty = fromField.getText().equals("") || toField.getText().equals("") ||
                        timeField.getText().equals("") || seatclassField.getText().equals("");

                if(!isEmpty) {
                    flightsList.getItems().clear();
                    try {
                         flights = dBhandler.avaiableFlights(
                                dBhandler.getCitiesId(fromField.getText()),
                                dBhandler.getCitiesId(toField.getText()),
                                timeField.getText()
                        );
                        for (int i = 0; i < flights.size(); i++) {
                            if(flights.get(i).getStatus().equals("INACTIVE")){
                                flights.remove(i);
                            }
                        }
                        for (int i = 0; i < flights.size(); i++) {
                            flightsList.getItems().add(dBhandler.getCitiesName(flights.get(i).getDeparture_city_id())
                                    + " - " + dBhandler.getCitiesName(flights.get(i).getArrival_city_id()) + " at "
                            + flights.get(i).getDeparture_time() + " price for business :[" + flights.get(i).getBusiness_place_price()
                            + "] ; price for economy :[" + flights.get(i).getEconomy_place_price() + "]");
                        }
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }
                }
            }
        });
        adminLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    ticketsManagmentLabel.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MenuController.class.getResource("admin.fxml"));
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
        logoutLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    logoutLabel.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MenuController.class.getResource("hello-view.fxml"));
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
        flightsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = flightsList.getSelectionModel().getSelectedIndex();
                ArrayList<Flights> flights = new ArrayList<>();
                try{
                    flights = dBhandler.avaiableFlights(
                            dBhandler.getCitiesId(fromField.getText()),
                            dBhandler.getCitiesId(toField.getText()),
                            timeField.getText());
                    Aircrafts aircraft = dBhandler.aircraftById(flights.get(index).getAircraft_id());
                    String desc = aircraft.getName() + " (" + aircraft.getModel() + ") \n" +
                           " business seats: (" + aircraft.getBusiness_class_capacity() +
                            ")\n economy seats: (" + aircraft.getEconomy_class_capacity() + ")\n" +
                            "Buying this ticket?";
                    flightDesc.setText(desc);
                    TicketTransactionHandler.flight = flights.get(index);
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            }
        });
        buyticketButton.setOnAction(actionEvent -> {
            boolean isNull = true;
            if(TicketTransactionHandler.flight!=null){
                isNull = false;
            }
            try{
                if(!isNull){
                    buyticketButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MenuController.class.getResource("ticketPurchase.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });

        ticketsManagmentLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    ticketsManagmentLabel.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MenuController.class.getResource("cashier.fxml"));
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
}
