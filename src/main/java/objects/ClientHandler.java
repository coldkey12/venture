package objects;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private Socket socket;
    private ArrayList<Cities> cities;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ArrayList<Cities> getCities() {
        return cities;
    }

    public void setCities(ArrayList<Cities> cities) {
        this.cities = cities;
    }

    public ClientHandler() {
    }

    public ClientHandler(Socket socket, ArrayList<Cities> cities) {
        this.socket = socket;
        this.cities = cities;
    }
    public void run() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(this.socket.getInputStream());
            String city = "";
            int answer = 0;
            while((city = (String) inputStream.readObject())!=null){
                for (int i = 0; i < this.cities.size(); i++) {
                    if(this.cities.get(i).getName()==city){
                        answer = this.cities.get(i).getId();
                        outputStream.writeObject(answer);
                    }
                }
            }
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
}
