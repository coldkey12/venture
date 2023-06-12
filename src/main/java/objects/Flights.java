package objects;

import java.io.Serializable;

public class Flights implements Serializable {
    private int id;
    private int aircraft_id;
    private int departure_city_id;
    private int arrival_city_id;
    private String departure_time;
    private double economy_place_price;
    private double business_place_price;
    private String status;

    public Flights() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAircraft_id() {
        return aircraft_id;
    }

    public void setAircraft_id(int aircraft_id) {
        this.aircraft_id = aircraft_id;
    }

    public int getDeparture_city_id() {
        return departure_city_id;
    }

    public void setDeparture_city_id(int departure_city_id) {
        this.departure_city_id = departure_city_id;
    }

    public int getArrival_city_id() {
        return arrival_city_id;
    }

    public void setArrival_city_id(int arrival_city_id) {
        this.arrival_city_id = arrival_city_id;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public double getEconomy_place_price() {
        return economy_place_price;
    }

    public void setEconomy_place_price(double economy_place_price) {
        this.economy_place_price = economy_place_price;
    }

    public double getBusiness_place_price() {
        return business_place_price;
    }

    public void setBusiness_place_price(double business_place_price) {
        this.business_place_price = business_place_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Flights(int id, int aircraft_id, int departure_city_id, int arrival_city_id, String departure_time, double economy_place_price, double business_place_price, String status) {
        this.id = id;
        this.aircraft_id = aircraft_id;
        this.departure_city_id = departure_city_id;
        this.arrival_city_id = arrival_city_id;
        this.departure_time = departure_time;
        this.economy_place_price = economy_place_price;
        this.business_place_price = business_place_price;
        this.status = status;
    }
}
