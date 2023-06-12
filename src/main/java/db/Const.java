package db;

public class Const {
    //db and tables
    public static final String tables_aircrafts = "aircrafts";
    public static final String tables_flights = "flights";
    public static final String tables_tickets = "tickets";
    public static final String tables_users = "users";
    public static final String db_aviatickets = "aviatickets";

    //aircrafts
    public static final String aircrafts_id = "id";
    public static final String aircrafts_name = "name";
    public static final String aircrafts_model = "model";
    public static final String aircrafts_business_class_capacity = "business_class_capacity";
    public static final String aircrafts_economy_class_capacity = "economy_class_capacity";

    //flights
    public static final String flights_id = "id";
    public static final String flights_aircraft_id = "aircraft_id";
    public static final String flights_departure_city_id = "departure_city_id";
    public static final String flights_arrival_city_id = "arrival_city_id";
    public static final String flights_departure_time = "departure_time";
    public static final String flights_economy_place_price = "economy_place_price";
    public static final String flights_business_place_price = "business_place_price";

    //tickets
    public static final String tickets_id = "id";
    public static final String tickets_flight_id = "flight_id";
    public static final String tickets_name = "name";
    public static final String tickets_surname = "surname";
    public static final String tickets_passport_number = "passport_number";
    public static final String tickets_ticket_type = "ticket_type";

    //users
    public static final String users_id = "id";
    public static final String users_user_role = "user_role";
    public static final String users_name = "name";
    public static final String users_surname = "surname";
    public static final String users_password = "password";
    public static final String users_username = "username";
}
