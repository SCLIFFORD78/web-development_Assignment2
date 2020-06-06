package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;
import java.util.ArrayList;
import java.util.List;

public class Accounts extends Controller
{
  public static void signup()
  {
    render("signup.html");
  }

  public static void login()
  {
    render("login.html");
  }

  public  void register(String name, String gender, String email, String password, String address,
                        double height, double startWeight,String accountType)
  {
    boolean valid = true;
    if (checkEmail(email)){
      if (accountType.equals("Member")){
        Logger.info("Registering new member " + email);
        Member member = new Member (name, gender, height, startWeight,email, password, address);
        member.setBmi(GymUtility.calculateBMI(member,null));
        member.setStatus(GymUtility.determineBMICategory(member.getBmi()));
        member.setIsIdealBodyWeight(GymUtility.isIdealBodyWeight(member,null));
        member.save();
        session.clear();
        session.put("logged_in_Memberid", member.id);
        redirect ("/dashboard");
      }else{
        Logger.info("Registering new trainer " + email);
        Trainer trainer = new Trainer (name, gender, height, startWeight,email, password, address);
        trainer.save();
        session.clear();
        session.put("logged_in_Trainerid", trainer.id);
        redirect("/dashboardTrainer");
      }
    }else {
      Logger.info("Email address already registered " + email);
      valid = false;
      render("signup.html", valid);}



  }

  public static void authenticate(String email, String password)
  {
    Logger.info("Attempting to authenticate with " + email + ":" + password);

    Member member = Member.findByEmail(email);
    Trainer trainer = Trainer.findByEmail(email);
    if ((member != null) && (member.checkPassword(password) == true)) {
      Logger.info("Authentication successful");
      Logger.info("Member "+ member.name);
      session.put("logged_in_Memberid", member.id);
      redirect ("/dashboard");
    }
    else if ((trainer != null) && (trainer.checkPassword(password) == true)){
      Logger.info("Authentication successful");
      session.put("logged_in_Trainerid", trainer.id);
      redirect ("/dashboardTrainer");
    }
    else {
      Logger.info("Authentication failed");
      redirect("/login");
    }
  }

  public static void logout()
  {
    session.clear();
    redirect ("/");
  }

  public static Member getLoggedInMember()
  {
    Member member = null;
    if (session.contains("logged_in_Memberid")) {
      String memberId = session.get("logged_in_Memberid");
      member = Member.findById(Long.parseLong(memberId));
    } else {
      login();
    }
    return member;
  }

  public static Trainer getLoggedInTrainer()
  {
    Trainer trainer = null;
    if (session.contains("logged_in_Trainerid")) {
      String trainerId = session.get("logged_in_Trainerid");
      trainer = Trainer.findById(Long.parseLong(trainerId));
    } else {
      login();
    }
    return trainer;
  }

  private boolean checkEmail(String email){
    List<Member> memberList = Member.findAll();
    List<Trainer> trainerList = Trainer.findAll();
    boolean result = true;
    for (int i =0;i<memberList.size();i++){
      if(email.equals(memberList.get(i).getEmail()) || email.isEmpty()) {
        result =   false;
      }
    }
    for (int i =0;i<trainerList.size();i++){
      if(email.equals(trainerList.get(i).getEmail()) || email.isEmpty()) {
        result =   false;
      }
    }
    return result;
  }
}