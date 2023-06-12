package com.example.aviatickets;

import db.DBhandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.Tickets;

import java.util.ArrayList;

public class CashierTicketsController {
    @FXML
    private ListView<String> CustomersRequestsList;

    @FXML
    private Button addButton;

    @FXML
    private ListView<String> allTicketsList;

    @FXML
    private Label backLabel;

    @FXML
    private Label backToCashier;

    @FXML
    private Label cashierUsername;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField flightIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passportIdField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField ticketTypeField;
    @FXML
    void initialize(){
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

        addButton.setOnAction(actionEvent -> {
            if(!(flightIdField.getText().equals("") || nameField.getText().equals("")
            || surnameField.getText().equals("") || passportIdField.getText().equals("")
            || ticketTypeField.getText().equals(""))){
                dBhandler.addTicket(Integer.parseInt(flightIdField.getText()),nameField.getText(),surnameField.getText(),
                        passportIdField.getText(),ticketTypeField.getText());
            }
        });

        backToCashier.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    backLabel.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloController.class.getResource("cashier.fxml"));
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
        ArrayList<Tickets> tickets = dBhandler.allTickets();
    }
}
