package com.example.aviatickets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Cities;
import objects.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HelloApplication extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),800,400);
        stage.setTitle("aviatickets");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();

//        ServerSocket serverSocket = new ServerSocket(8000);
//
//        Cities ALA = new Cities(1,"Almaty","UAAA");
//        Cities LGA = new Cities(2,"New-York","KLGA");
//        Cities LHR = new Cities(3,"London","EGLL");
//        Cities HND = new Cities(3,"Tokyo","RJTT");
//        ArrayList<Cities> cities = new ArrayList<>();
//        cities.add(ALA);
//        cities.add(LGA);
//        cities.add(LHR);
//        cities.add(HND);
//
//        while (true){
//            Socket socket = serverSocket.accept();
//            ClientHandler clientHandler = new ClientHandler(socket,cities);
//            clientHandler.start();
//        }
    }
}