package e.arturo.nuevomedummasperron4kfullhd1link.Model;

public class Order {
    private String Houseid;
    private String houseName;
    private String Phone;
    private String houseAddress;
    private String Email;

    public Order() {
    }

    public Order(String houseid, String houseName, String phone, String houseAddress, String email) {
        Houseid = houseid;
        this.houseName = houseName;
        Phone = phone;
        this.houseAddress = houseAddress;
        Email = email;
    }

    public String getHouseid() {
        return Houseid;
    }

    public void setHouseid(String houseid) {
        Houseid = houseid;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
