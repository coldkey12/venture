package com.example.aviatickets;

import db.DBhandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.CurrentUser;
import objects.Flights;
import objects.TicketTransactionHandler;
import objects.TicketsCounterHandler;

public class TicketPurchaseController {
    @FXML
    private Label backLabel;

    @FXML
    private Button confirmationButton;

    @FXML
    private Label infoLabel;

    @FXML
    private TextField passportId;

    @FXML
    private ChoiceBox<String> ticketClassChoice;
    @FXML
    private Label ticketsCounter;
    @FXML
    private Button cancelTickets;

    @FXML
    void initialize(){
        TicketsCounterHandler.counter = 0;
        ObservableList<String> langs = FXCollections.observableArrayList("Business","Economy");
        ticketClassChoice.setItems(langs);
        DBhandler dBhandler = new DBhandler();
        try{
            Flights flight = TicketTransactionHandler.flight;
            infoLabel.setText(dBhandler.getCitiesName(flight.getDeparture_city_id())
                    + " - " + dBhandler.getCitiesName(flight.getArrival_city_id()) + " at "
                    + flight.getDeparture_time() + " price for business :[" + flight.getBusiness_place_price()
                    + "] ; price for economy :[" + flight.getEconomy_place_price() + "]");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }

        backLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    backLabel.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(FlightsController.class.getResource("menu.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    TicketTransactionHandler.flight = null;
                } catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            }
        });

        confirmationButton.setOnAction(actionEvent -> {
            int counterTemp = TicketsCounterHandler.counter;
            counterTemp++;
            try {
                if (!passportId.getText().equals("") && ticketClassChoice.getValue() != null && TicketTransactionHandler.flight!=null) {
                    dBhandler.makeTicket(TicketTransactionHandler.flight.getId(), CurrentUser.firstname,
                            CurrentUser.secondname, passportId.getText(),ticketClassChoice.getValue());
                    ticketsCounter.setText("Tickets bought: " + counterTemp);
                    CurrentUser.passport_number = passportId.getText();
                }
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
            TicketsCounterHandler.counter = counterTemp;
        });

    }
}
