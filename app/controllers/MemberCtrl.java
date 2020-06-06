package controllers;

import java.util.List;

import models.Member;
import models.Trainer;
import models.Assessment;
import play.Logger;
import play.mvc.Controller;
import java.util.Date;
import java.text.SimpleDateFormat;


public class MemberCtrl extends Controller
{

  public static void index(Long id)
  {
    Member member = Accounts.getLoggedInMember();
    Logger.info ("Rendering member.html with Members id = " + member.id);
    render("member.html", member);
  }

  public static void updateMemberAccount(String name, String gender, String email, String password, String address, double height, double startWeight){
    Member member = Accounts.getLoggedInMember();
    if (!name.isEmpty())member.setName(name);
    if (!gender.isEmpty())member.setGender(gender);
    if (!password.isEmpty())member.setPassword(password);
    if (!address.isEmpty())member.setAddress(address);
    if (height != 0)member.setHeight(height);
    if (startWeight != 0)member.setStartWeight(startWeight);
    member.save();
    member.setBmi(GymUtility.calculateBMI(member,null));
    member.setStatus(GymUtility.determineBMICategory(member.getBmi()));
    Logger.info ("Updating member details = " + member.email);
    render("dashboard.html", member);
  }

  public  void addAssessment(Long id, double weight, double chest, double thigh,
                                    double upperArm, double waist, double hips, String comments, String date)
  {
    Assessment assessment = new Assessment(weight, chest, thigh, upperArm, waist, hips, comments, dateToday());
    Member member = Member.findById(id);
    member.getAssessments().add(0,assessment);
    //updates the trend icon status compareing assessment relative to eachother
    displayProgressByWeight(member);
    member.setBmi(GymUtility.calculateBMI(member,assessment));
    member.setStatus(GymUtility.determineBMICategory(GymUtility.calculateBMI(member, assessment)));
    member.setIsIdealBodyWeight(GymUtility.isIdealBodyWeight(member, assessment));
    member.save();
    redirect ("/dashboard");

  }

  private String dateToday(){
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/YY hh:mm:ss");
    String formatted = df.format(new Date());
    return formatted;
  }

  public static void deleteMember (Long id)
  {
    Member member = Member.findById(id);
    Logger.info ("Removing Member" + id);
    member.delete();
    redirect ("/dashboardTrainer");

  }

  public  void deleteAssessment (Long assessmentId)
  {
    Member member = Accounts.getLoggedInMember();
    Assessment assessment = Assessment.findById(assessmentId);
    member.getAssessments().remove(assessment);
    member.save();
    displayProgressByWeight(member);
    Logger.info ("Removing Assessment" + assessmentId);

    redirect ("/dashboard");

  }

  /**
   * @discription Updates the trend arrow indicator on getAssessments for a member
   * @param member
   */

  private void displayProgressByWeight(Member member){
    List<Assessment> assessmentList = member.getAssessments();
    String trend = "";
    System.out.println(assessmentList.size());
    if (assessmentList.size() !=0){
      if(assessmentList.size() == 1){
        if(assessmentList.get(0).weight > member.startWeight){
          member.getAssessments().get(0).setTrend("Plus") ;
        }
        else if (assessmentList.get(0).weight == member.startWeight){
          member.getAssessments().get(0).setTrend("No Change");
        }
        else {
          member.getAssessments().get(0).setTrend("Down");
        }
      }else{
        for(int i = 0; i < assessmentList.size()-1 ; i++){
          if (assessmentList.get(i).getWeight() > assessmentList.get(i+1).getWeight()) {
            member.getAssessments().get(i).setTrend("Plus");trend = "Plus";
          }
          else if (assessmentList.get(i).weight == assessmentList.get(i+1).weight){
            member.getAssessments().get(i).setTrend("No Change");trend = "No Change";
          }
          else {
            member.getAssessments().get(i).setTrend("Down");trend = "Down";
          }
        }
      }
      member.setBmi(GymUtility.calculateBMI(member,assessmentList.get(0)));
      member.setStatus(GymUtility.determineBMICategory(GymUtility.calculateBMI(member, assessmentList.get(0))));
      member.setIsIdealBodyWeight(GymUtility.isIdealBodyWeight(member, assessmentList.get(0)));
    }
    member.setBmi(GymUtility.calculateBMI(member,null));
    member.setStatus(GymUtility.determineBMICategory(GymUtility.calculateBMI(member, null)));
    member.setIsIdealBodyWeight(GymUtility.isIdealBodyWeight(member, null));

  }


  public static double roundAvoid(double value, int places) {
    double scale = Math.pow(10, places);
    return Math.round(value * scale) / scale;
  }


  }








