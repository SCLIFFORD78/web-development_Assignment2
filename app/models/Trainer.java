package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Trainer extends Model
{
    public String name;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Member> memberList = new ArrayList<Member>();
    public String gender;
    public double height;
    public double startWeight;
    public String email;
    public String password;
    public String address;
    public String bmi;
    public String status;
    public boolean isIdealBodyWeight;



    public Trainer(String name, String gender, double height, double startWeight, String email,
                   String password, String address)
    {
        setName(name);
        setGender(gender);
        setHeight(height);
        setStartWeight(startWeight);
        this.email = email;
        setPassword(password);
        setAddress(address);
        setBmi(bmi);
        setStatus(status);
        setIsIdealBodyWeight(isIdealBodyWeight);

    }

    //mutator's


    public List<Member> getMemberList() {
        return memberList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(double height) {
        if (height<1)height=1;
        else if (height>3)height=3;
        this.height = height ;
    }

    public void setStartWeight(double startWeight) {
        if (startWeight<35)startWeight=35;
        else if (startWeight>250)startWeight=250;
        this.startWeight = startWeight ;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIsIdealBodyWeight(boolean idealBodyWeight) {
        isIdealBodyWeight = idealBodyWeight;
    }


    //Accessor's

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public double getHeight() {
        return height;
    }

    public double getStartWeight() {
        return startWeight;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getBmi() {
        return bmi;
    }

    public String getStatus() {
        return status;
    }

    public boolean isIdealBodyWeight() {
        return isIdealBodyWeight;
    }


    public static Trainer findByEmail(String email)
    {
        return find("email", email).first();
    }

    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }

}