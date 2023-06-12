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
import objects.AdminHandler;
import objects.Cities;
import objects.CurrentUser;
import objects.Flights;

import java.util.ArrayList;

public class AdminCitiesController {
    @FXML
    private ListView<String> CityRequestsList;

    @FXML
    private TextField IcaoField;

    @FXML
    private Button addButton;

    @FXML
    private Label adminUsernameLabel;

    @FXML
    private ListView<String> allCtitiesList;

    @FXML
    private Label backLabel;

    @FXML
    private TextField cityNameField;

    @FXML
    private Button deleteButton;

    @FXML
    private Label flightsButton;

    @FXML
    private Button kill;

    @FXML
    private Label planesButton;

    @FXML
    private Button reviveButton;
   @FXML
    void initialize(){
       DBhandler dBhandler = new DBhandler();
       reloadListView();

       adminUsernameLabel.setText(CurrentUser.username);

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
                   loader.setLocation(AdminCitiesController.class.getResource("admin.fxml"));
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
       planesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {
               try{
                   planesButton.getScene().getWindow().hide();
                   FXMLLoader loader = new FXMLLoader();
                   loader.setLocation(AdminCitiesController.class.getResource("adminPlanes.fxml"));
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

       addButton.setOnAction(actionEvent -> {
           try{
               if(!(cityNameField.getText().equals("") || IcaoField.getText().equals(""))){
                   dBhandler.addCity(cityNameField.getText(),IcaoField.getText());
               }
           }catch (Exception exception){
               exception.getMessage();
           }
       });

       allCtitiesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {
               int index = allCtitiesList.getSelectionModel().getSelectedIndex();
               ArrayList<Cities> cities = dBhandler.allCities();
               try{
                   AdminHandler.city = cities.get(index);
               }catch (Exception exception){
                   System.out.println(exception.getMessage());
               }
           }
       });

       kill.setOnAction(actionEvent -> {
           ArrayList<Cities> cities = dBhandler.allCities();
           try{
               for (int i = 0; i < cities.size(); i++) {
                   if(cities.get(i).getId() == AdminHandler.city.getId()){
                       dBhandler.killCity(cities.get(i).getId());
                       AdminHandler.city = null;
                   }
               }
           }catch (Exception exception){
               System.out.println(exception.getMessage());
           }
           reloadListView();
       });

       reviveButton.setOnAction(actionEvent -> {
           ArrayList<Cities> cities = dBhandler.allCities();
           try{
               for (int i = 0; i < cities.size(); i++) {
                   if(cities.get(i).getId() == AdminHandler.city.getId()){
                       dBhandler.reviveCity(cities.get(i).getId());
                       AdminHandler.city = null;
                   }
               }
           }catch (Exception exception){
               System.out.println(exception.getMessage());
           }
           reloadListView();
       });

       deleteButton.setOnAction(actionEvent -> {
           ArrayList<Cities> cities = dBhandler.allCities();
           try{
               for (int i = 0; i < cities.size(); i++) {
                   if(cities.get(i).getId() == AdminHandler.city.getId()){
                       dBhandler.inactiveCity(cities.get(i).getId());
                       AdminHandler.city = null;
                   }
               }
           }catch (Exception exception){
               System.out.println(exception.getMessage());
           }
           reloadListView();
       });
   }
   public void reloadListView(){
       DBhandler dBhandler = new DBhandler();
       allCtitiesList.getItems().clear();
       ArrayList<Cities> cities = dBhandler.allCities();

       for (int i = 0; i < cities.size(); i++) {
           if(cities.get(i).getStatus().equals("DEAD")){
                cities.remove(i);
           }
       }

       for (int i = 0; i < cities.size(); i++) {
           allCtitiesList.getItems().add(cities.get(i).getName() + " - ICAO(" + cities.get(i).getIcao() + ") status"
           + cities.get(i).getStatus());
       }
   }
}
