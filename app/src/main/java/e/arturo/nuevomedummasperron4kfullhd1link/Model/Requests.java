package e.arturo.nuevomedummasperron4kfullhd1link.Model;

import java.util.List;

public class Requests {
    private String phone;
    private String name;
    private String email;
    private String coment;
    //private String status;
    private List<Order> house;

    public Requests(String phone, String name, String email, String coment, List<Order> house) {
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.coment = coment;
        this.house = house;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public List<Order> getHouse() {
        return house;
    }

    public void setHouse(List<Order> house) {
        this.house = house;
    }
}
