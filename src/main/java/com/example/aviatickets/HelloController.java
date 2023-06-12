package com.example.aviatickets;

import animations.Shaker;
import db.DBhandler;
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private Label alertLabelFlights;

    @FXML
    private Label alertLabelLogin;

    @FXML
    private TextArea flightDesc;

    @FXML
    private ListView<String> flightsList;

    @FXML
    private TextField fromField;

    @FXML
    private Label invalidLabelAlert;

    @FXML
    private Button loginButton;

    @FXML
    private Label makeAccountLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button searchForFlightsButton;

    @FXML
    private TextField seatclassField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField toField;

    @FXML
    private TextField usernameField;
    @FXML
    void initialize() throws IOException {
//        Socket socket = new Socket("127.0.0.1",8000);
//        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        System.out.println(CurrentUser.role);
        DBhandler dBhandler = new DBhandler();
        loginButton.setOnAction(actionEvent -> {
            boolean gapFlag = false;

            Shaker shaker = new Shaker(usernameField);
            Shaker shaker1 = new Shaker(passwordField);

            if(usernameField.getText().equals("") || passwordField.getText().equals("")){
                shaker.shake();
                shaker1.shake();
                gapFlag=true;
                alertLabelLogin.setText("Fill all the gaps");
            }

            if((dBhandler.login(usernameField.getText(), passwordField.getText())) && !gapFlag) {

                try {
                    loginButton.getScene().getWindow().hide();
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
            } else {
                invalidLabelAlert.setText("Invalid password or username");
            }
        });
        makeAccountLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    makeAccountLabel.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloController.class.getResource("signup.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }catch(Exception exception){
                    System.out.println(exception.getCause());
                }
            }
        });

        searchForFlightsButton.setOnAction(actionEvent -> {
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
                        flightsList.getItems().add(dBhandler.getCitiesName(flights.get(i).getDeparture_city_id())
                                + " - " + dBhandler.getCitiesName(flights.get(i).getArrival_city_id()) + " at "
                                + flights.get(i).getDeparture_time() + " price for business :[" + flights.get(i).getBusiness_place_price()
                                + "] ; price for economy :[" + flights.get(i).getEconomy_place_price() + "]");
                    }
                } catch (Exception exception) {
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
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            }
        });
    }
}