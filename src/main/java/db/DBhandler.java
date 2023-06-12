package db;
import objects.*;

import java.sql.*;
import java.util.ArrayList;

public class DBhandler {
    Connection dbConnection;
    public DBhandler(){}

    public Connection getDbConnection() throws SQLException, ClassNotFoundException {
        String connectionString = "jdbc:mysql://localhost:3306/aviatickets";
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.dbConnection = DriverManager.getConnection(connectionString, "root", "");
        return this.dbConnection;
    }

    public boolean checkExistance(String username){
        String insert = "SELECT * FROM " + Const.tables_users + " WHERE " + Const.users_username + " =?";
        boolean flag = false;
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                flag=true;
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return flag;
    }

    public void signUp(String username, String password, String first_name, String second_name){
        String insert = "INSERT INTO " + Const.tables_users + "(" + Const.users_user_role + "," +
                Const.users_name + "," + Const.users_surname + "," + Const.users_password + "," +
                Const.users_username + ")" + " VALUES(?,?,?,?,?)";
        try{

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1,1);
            preparedStatement.setString(2,first_name);
            preparedStatement.setString(3,second_name);
            preparedStatement.setString(4,password);
            preparedStatement.setString(5,username);
            preparedStatement.executeUpdate();

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public boolean login(String username, String password){
        String insert = "SELECT * FROM " + Const.tables_users + " WHERE " + Const.users_username + " =? && " +
                Const.users_password + " =?";
        boolean flag = false;
        User user = new User();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                    flag = true;
                    user = new User(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3),
                            resultSet.getString(4),resultSet.getString(5),resultSet.getString(6));
                }
                CurrentUser.id = user.getId();
                CurrentUser.role = user.getUser_role();
                CurrentUser.firstname = user.getName();
                CurrentUser.secondname = user.getSurname();
                CurrentUser.username = user.getUsername();
                CurrentUser.password = user.getPassword();

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return flag;
    }

    public ArrayList<Flights> avaiableFlights(int dep_id, int arr_id, String dep_time){
        String insert = "SELECT * FROM " + Const.tables_flights + " WHERE " + Const.flights_departure_city_id +
                " =? && " + Const.flights_arrival_city_id + " =? && " + Const.flights_departure_time + " =?";
        ArrayList<Flights> flights = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1,dep_id);
            preparedStatement.setInt(2,arr_id);
            preparedStatement.setString(3,dep_time);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                flights.add(new Flights(resultSet.getInt(1),resultSet.getInt(2),
                        resultSet.getInt(3),resultSet.getInt(4),resultSet.getString(5),
                        resultSet.getDouble(6),resultSet.getDouble(7),resultSet.getString(8)));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return flights;
    }

    public ResultSet avaiableFlightsResultset(int dep_id, int arr_id, String dep_time){
        String insert = "SELECT * FROM " + Const.tables_flights + " WHERE " + Const.flights_departure_city_id +
                " =? && " + Const.flights_arrival_city_id + " =? && " + Const.flights_departure_time + " =?";
        ResultSet resultSet = null;
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1,dep_id);
            preparedStatement.setInt(2,arr_id);
            preparedStatement.setString(3,dep_time);
            resultSet = preparedStatement.executeQuery();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return resultSet;
    }

    public String getCitiesName(int id){
        String insert = "SELECT name FROM cities WHERE id =?";
        String cityName = "";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            cityName = resultSet.getString(1);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return cityName;
    }

    public int getCitiesId(String name){
        String insert = "SELECT id FROM cities WHERE name =?";
        int id = 0;
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return id;
    }

    public Aircrafts aircraftById(int id){
        String insert = "SELECT * FROM aircrafts WHERE id =?";
        Aircrafts aircraft = new Aircrafts();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            aircraft = new Aircrafts(resultSet.getInt(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5),resultSet.getString(6));
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return aircraft;
    }

    public void makeTicket(int flight_id, String name, String surname, String passport_number,String ticket_type){
        String insert = "INSERT INTO tickets(flight_id,name,surname,passport_number,ticket_type) VALUES(?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1,flight_id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,surname);
            preparedStatement.setString(4,passport_number);
            preparedStatement.setString(5,ticket_type);
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public ArrayList<Flights> allFlights(){
        String insert = "SELECT * FROM flights";
        ArrayList<Flights> flights = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                flights.add(new Flights(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getInt(3), resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getDouble(6), resultSet.getDouble(7), resultSet.getString(8)));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return flights;
    }

    public ArrayList<Tickets> AllMyTickets(){
        String insert = "SELECT * FROM tickets WHERE " + Const.tickets_passport_number + " =? && " + Const.tickets_name + " =? && " + Const.tickets_surname + " =?";
        ArrayList<Tickets> myTickets = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,CurrentUser.passport_number);
            preparedStatement.setString(2,CurrentUser.firstname);
            preparedStatement.setString(3,CurrentUser.secondname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                myTickets.add(new Tickets(resultSet.getInt(1)
                        ,resultSet.getInt(2),resultSet.getString(3)
                        ,resultSet.getString(4),resultSet.getString(5)
                        ,resultSet.getString(6),resultSet.getString(7)));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return myTickets;
    }

    public Flights flightById(int id){
        String insert = "SELECT * FROM flights WHERE id =?";
        Flights flight = new Flights();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            flight = new Flights(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),
                    resultSet.getInt(4),resultSet.getString(5),resultSet.getDouble(6),resultSet.getDouble(7),resultSet.getString(8));
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return flight;
    }

    public void addFlight(int aircraft_id,int departure_city_id, int arrival_city_id, String departure_time, double economy_place_price, double business_place_price){
        String insert = "INSERT INTO " + Const.tables_flights + "(" + Const.flights_aircraft_id + "," +
                Const.flights_departure_city_id + "," + Const.flights_arrival_city_id + "," +
                Const.flights_departure_time + "," + Const.flights_economy_place_price + "," +
                Const.flights_business_place_price + ", status) VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1,aircraft_id);
            preparedStatement.setInt(2,departure_city_id);
            preparedStatement.setInt(3,arrival_city_id);
            preparedStatement.setString(4,departure_time);
            preparedStatement.setDouble(5,economy_place_price);
            preparedStatement.setDouble(6,business_place_price);
            preparedStatement.setString(7,"ACTIVE");
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public void makeInactiveFlight(int id){
        String insert = "UPDATE " + Const.tables_flights + " \n" +
                "SET status = ? \n" +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"INACTIVE");
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void reviveFlight(int id){
        String insert = "UPDATE " + Const.tables_flights + " \n" +
                "SET status = ? \n" +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"ACTIVE");
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void killFlight(int id) {
        String insert = "UPDATE " + Const.tables_flights + " \n" +
                "SET status = ? \n" +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"DEAD");
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<Aircrafts> allAircrafts(){
        String insert = "SELECT * FROM " + Const.tables_aircrafts;
        ArrayList<Aircrafts> planes = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                planes.add(new Aircrafts(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5),resultSet.getString(6)));
            }
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return planes;
    }

    public void killPlane(int id) {
        String insert = "UPDATE " + Const.tables_aircrafts + " \n" +
                "SET status = ? \n" +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"DEAD");
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void revivePlane(int id) {
        String insert = "UPDATE " + Const.tables_aircrafts + " \n" +
                "SET status = ? \n" +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"ACTIVE");
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void inactivePlane(int id) {
        String insert = "UPDATE " + Const.tables_aircrafts + " \n" +
                "SET status = ? \n" +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"INACTIVE");
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addAircraft(String name, String model, int business_capacity, int economy_capacity){
        String insert = "INSERT INTO " + Const.tables_aircrafts + "(" + Const.aircrafts_name + "," + Const.aircrafts_model
                + "," + Const.aircrafts_business_class_capacity + "," + Const.aircrafts_economy_class_capacity + ",status) VALUES(?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,model);
            preparedStatement.setInt(3,business_capacity);
            preparedStatement.setInt(4,economy_capacity);
            preparedStatement.setString(5,"ACTIVE");
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public ArrayList<Cities> allCities(){
        String insert = "SELECT * FROM cities";
        ArrayList<Cities> cities = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                cities.add(new Cities(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4)));
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return cities;
    }

    public void killCity(int id){
        String insert = "UPDATE cities  \n" +
                "SET status = ? \n" +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"DEAD");
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void reviveCity(int id){
        String insert = "UPDATE cities  \n" +
                "SET status = ? \n" +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"ACTIVE");
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void inactiveCity(int id){
        String insert = "UPDATE cities  \n" +
                "SET status = ? \n" +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,"INACTIVE");
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addCity(String name, String icao){
        String insert = "INSERT INTO cities(name, icao, status) VALUES(?,?,?)";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,icao);
            preparedStatement.setString(3,"ACTIVE");
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public void addTicket(int flight_id, String name, String surname, String passport, String ticket_type){
        String insert = "INSERT INTO tickets(flight_id,name,surname,passport_number,ticket_type,status) VALUES(?,?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1,flight_id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,surname);
            preparedStatement.setString(4,passport);
            preparedStatement.setString(5,ticket_type);
            preparedStatement.setString(6,"ACTIVE");
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public ArrayList<Tickets> allTickets(){
        String insert = "SELECT * FROM tickets";
        ArrayList<Tickets> tickets = new ArrayList<>();
        try{

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return tickets;
    }
}
