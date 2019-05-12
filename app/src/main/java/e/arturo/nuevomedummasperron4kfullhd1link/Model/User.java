package e.arturo.nuevomedummasperron4kfullhd1link.Model;

public class User {

    private String Name;
    private  String Password;
    private String Email;
    private String Phone;
    private String SecureCode;

    public User() {
    }

    public User(String phone, String email, String name, String password,String secureCode) {
        Phone = phone;
        Email = email;
        Name = name;
        Password = password;
        SecureCode = secureCode;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSecureCode() {
        return SecureCode;
    }

    public void setSecureCode(String secureCode) {
        SecureCode = secureCode;
    }
}
