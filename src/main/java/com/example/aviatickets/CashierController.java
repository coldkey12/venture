package com.example.aviatickets;

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
import objects.CurrentUser;

public class CashierController {
    @FXML
    private ListView<String> CustomersRequestsList;

    @FXML
    private ListView<String> allTicketsList;

    @FXML
    private Button applyButton;

    @FXML
    private Label backLabel;

    @FXML
    private Label cashierUsername;

    @FXML
    private ChoiceBox<String> paramChoice;

    @FXML
    private TextField paramTextField;
    @FXML
    private Label addTicket;

    @FXML
    void initialize(){
        ObservableList<String> langs = FXCollections.observableArrayList("Flight id","Name","Surname","Passport number", "Ticket type");
        paramChoice.setItems(langs);

        cashierUsername.setText(CurrentUser.username);

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

        addTicket.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    addTicket.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(CashierController.class.getResource("cashierAddtickets.fxml"));
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
