package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Member;
import models.Trainer;
import models.Assessment;
import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller
{
    public static void index()
    {
        Logger.info("Rendering Dashboard");
        Member member = Accounts.getLoggedInMember();
        member.setAssessments(member.getAssessments());
        render("dashboard.html", member);
    }
}