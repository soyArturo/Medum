package e.arturo.nuevomedummasperron4kfullhd1link.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import e.arturo.nuevomedummasperron4kfullhd1link.Common.Common;

public class House {
    private String Title;
    private String Address;
    private String LatLng;
    private String Image;
    private String Description;
    private String Price;
    private String Currency;
    private String pushId;
    private String Phone;
    private String Email;
    private String Date;
    private String Bedroom;
    private String Bathroom;
    private String Size;
    private String userID;

    public House() {
    }

    public House(String title, String address, String latLng,String image, String description, String price, String currency, String phone, String email, String date, String bedroom, String bathroom, String size, String userID) {
        Title = title;
        Address = address;
        LatLng = latLng;
        Image = image;
        Description = description;
        Price = price;
        Currency = currency;
        this.pushId = pushId;
        Phone = phone;
        Email = email;
        Date = date;
        Bedroom = bedroom;
        Bathroom = bathroom;
        Size = size;
        this.userID = userID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLatLng() {
        return LatLng;
    }

    public void setLatLng(String latLng) {
        LatLng = latLng;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBedroom() {
        return Bedroom;
    }

    public void setBedroom(String bedroom) {
        Bedroom = bedroom;
    }

    public String getBathroom() {
        return Bathroom;
    }

    public void setBathroom(String bathroom) {
        Bathroom = bathroom;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
