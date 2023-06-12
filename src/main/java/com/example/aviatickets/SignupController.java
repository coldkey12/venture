package com.example.aviatickets;

import animations.Shaker;
import db.DBhandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    private Label alertLabel;

    @FXML
    private TextField firstnameField;

    @FXML
    private Label loginLabel;

    @FXML
    private Label nameAlert;

    @FXML
    private Label passwordAlert;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label secondnameAlert;

    @FXML
    private TextField secondnameField;

    @FXML
    private Button signupButton;

    @FXML
    private Label usernameAlert;

    @FXML
    private Label usernameExistanceLabelAlert;

    @FXML
    private TextField usernameField;

    @FXML
    void initialize(){
        DBhandler dBhandler = new DBhandler();
        loginLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{

                    loginLabel.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SignupController.class.getResource("hello-view.fxml"));
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
        signupButton.setOnAction(actionEvent -> {

            Shaker shaker = new Shaker(usernameField);
            Shaker shaker1 = new Shaker(firstnameField);
            Shaker shaker2 = new Shaker(secondnameField);
            Shaker shaker3 = new Shaker(passwordField);

            boolean usernameLengthFlag = false;
            boolean passwordLengthFlag = false;
            boolean nameLengthFlag = false;
            boolean surnameLengthFlag = false;
            boolean usernameExistanceFlag = false;
            boolean allFieldsEmpty = false;

            if(usernameField.getText().length()>14){
                shaker.shake();
                usernameField.setText("");
                usernameLengthFlag=true;
                usernameAlert.setText("Username is too long");
            }

            if(passwordField.getText().length()>8) {
                shaker3.shake();
                passwordField.setText("");
                passwordLengthFlag=true;
                passwordAlert.setText("Password is too long");
            }

            if(firstnameField.getText().length()>20){
                shaker1.shake();
                firstnameField.setText("");
                nameLengthFlag=true;
                nameAlert.setText("Name is too long");
            }

            if(secondnameField.getText().length()>20){
                shaker2.shake();
                secondnameField.setText("");
                surnameLengthFlag=true;
                secondnameAlert.setText("Second name is too long");
            }

            if(dBhandler.checkExistance(usernameField.getText())){
                shaker.shake();
                usernameField.setText("");
                usernameExistanceFlag=true;
                usernameExistanceLabelAlert.setText("This username is taken already");
            }

            if(usernameField.getText().equals("") || passwordField.getText().equals("") ||
                    secondnameField.getText().equals("") || firstnameField.getText().equals("")){
                allFieldsEmpty=true;
                alertLabel.setText("Fill all the gaps");
                shaker.shake();
                shaker1.shake();
                shaker2.shake();
                shaker3.shake();
            }
            try{

                if(usernameLengthFlag == false && passwordLengthFlag == false && nameLengthFlag == false &&
                        surnameLengthFlag == false && usernameExistanceFlag == false && allFieldsEmpty == false){
                    dBhandler.signUp(usernameField.getText(),passwordField.getText(),
                            firstnameField.getText(),secondnameField.getText());

                    signupButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SignupController.class.getResource("hello-view.fxml"));
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
    }
}
