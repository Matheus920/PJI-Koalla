package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleIntegerProperty id;
    private SimpleStringProperty firstname;
    private SimpleStringProperty lastname;
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty dob;
    private SimpleStringProperty gender;
    
    public User(int id, String firstname, String lastname, String username, String password, String dob, String gender) {
        this.id = new SimpleIntegerProperty(id);
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.dob = new SimpleStringProperty(dob);
        this.gender = new SimpleStringProperty(gender);
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    
    public void setFirstName(String firstname) {
        this.firstname.set(firstname);
    }
    
    public void setLastName(String lastname) {
        this.lastname.set(lastname);
    }
    
    public void setUserName(String username) {
        this.username.set(username);
    }
    
    public void setPassword(String password) {
        this.password.set(password);
    }
    
    public void setDob(String dob) {
        this.dob.set(dob);
    }
    
    public void setGender(String gender) {
        this.gender.set(gender);
    }
    
    public int getId() {
        return id.get();
    }
    
    public String getFirstName() {
        return firstname.get();
    }
    
    public String getLastName() {
        return lastname.get();
    }
    
    public String getUserName() {
        return username.get();
    }
    
    public String getPassword() {
        return password.get();
    }
    
    public String getDob() {
        return dob.get();
    }
    
    public String getGender() {
        return gender.get();
    }
    
    public SimpleIntegerProperty idProperty() {
        return id;
    }
    
    public SimpleStringProperty firstNameProperty() {
        return firstname;
    }
    
    public SimpleStringProperty lastNameProperty() {
        return lastname;
    }
    
    public SimpleStringProperty userNameProperty() {
        return username;
    }
    
    public SimpleStringProperty passwordProperty() {
        return password;
    }
    
    public SimpleStringProperty dobProperty() {
        return dob;
    }
    
    public SimpleStringProperty genderProperty() {
        return gender;
    }
}
