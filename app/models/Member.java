package models;

import java.util.ArrayList;
import java.util.List;
import java.util.*;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Member extends Model
{
    public String name;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Assessment> assessments = new ArrayList<Assessment>();
    public String gender;
    public double height;
    public double startWeight;
    public String email;
    public String password;
    public String address;
    public double bmi;
    public String status;
    public boolean isIdealBodyWeight;




    public Member(String name, String gender, double height, double startWeight, String email, String password, String address)
    {
        setName(name);
        setGender(gender);
        setHeight(height);
        setStartWeight(startWeight);
        this.email = email;
        setPassword(password);
        setAddress(address);
        //setBmi(bmi);
        setStatus(status);
        setIsIdealBodyWeight(isIdealBodyWeight);
        setAssessments(assessments);
    }




    //mutator's


    public void setAssessments(List<Assessment> assessments) {
        this.assessments = sortedDates(assessments);
    }

    public List<Assessment> getAssessments() {

        return assessments;
    }
    public List<Assessment> sortedDates(List<Assessment> assessments){
        List<String> result = new ArrayList<String>();
        List<Assessment> output = new ArrayList<Assessment>();
        if (!assessments.isEmpty()){
            for (int i = 0; i<assessments.size();i++){
                result.add(assessments.get(i).getDate());
            }
            // System.out.println("result "+result.size());
            // System.out.println("assessments "+assessments.size());
            Collections.sort(result, Collections.reverseOrder());
            for (int p = 0; p < result.size();p++){
                for (int j = 0; j < assessments.size(); j++){
                    // System.out.println(p + "  "+ j);
                    if (result.get(p).equals(assessments.get(j).getDate())){
                        output.add(assessments.get(j));
                        //System.out.println(output.get(j).getDate());
                    }
                }
            }
            //System.out.println(output.size());
            //for (int i = 0;i < )
            //System.out.println(result);

            return output;
        }
        return assessments;
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

    public void setBmi(double bmi) {
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

    public double getBmi() {
        return bmi;
    }

    public String getStatus() {
        return status;
    }

    public boolean isIdealBodyWeight() {
        return isIdealBodyWeight;
    }


    public static Member findByEmail(String email)
    {
        return find("email", email).first();
    }

    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }


}