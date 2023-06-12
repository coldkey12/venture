package objects;

import java.io.Serializable;

public class Cities implements Serializable {
    private int id;
    private String name;
    private String icao;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cities() {
    }

    public Cities(int id, String name, String icao, String status) {
        this.id = id;
        this.name = name;
        this.icao = icao;
        this.status = status;
    }
}
