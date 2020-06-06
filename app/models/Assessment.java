package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Assessment extends Model
{
  public double weight;
  public double chest;
  public double thigh;
  public double upperArm;
  public double waist;
  public double hips;
  public String comments;
  public String date;
  public String trend;

  public Assessment(double weight, double chest, double thigh, double upperArm, double waist, double hips, String comments, String date)
  {
    setWeight(weight);
    setChest(chest);
    setThigh(thigh);
    setUpperArm(upperArm);
    setWaist(waist);
    setHips(hips);
    setComments(comments);
    setDate(date);
    setTrend(trend);
  }

  //mutator's


  public void setWeight(double weight) {
    this.weight = weight;
  }

  public void setChest(double chest) {
    this.chest = chest;
  }

  public void setThigh(double thigh) {
    this.thigh = thigh;
  }

  public void setUpperArm(double upperArm) {
    this.upperArm = upperArm;
  }

  public void setWaist(double waist) {
    this.waist = waist;
  }

  public void setHips(double hips) {
    this.hips = hips;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setTrend(String trend) {
    this.trend = trend;
  }

  //Accessor's


  public double getWeight() {
    return weight;
  }

  public double getChest() {
    return chest;
  }

  public double getThigh() {
    return thigh;
  }

  public double getUpperArm() {
    return upperArm;
  }

  public double getWaist() {
    return waist;
  }

  public double getHips() {
    return hips;
  }

  public String getComments() {
    return comments;
  }

  public String getDate() {
    return date;
  }

  public String getTrend() {
    return trend;
  }
}