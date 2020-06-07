package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Member;
import models.Trainer;
import models.Assessment;
import play.Logger;
import play.mvc.Controller;

public class DashboardTrainer extends Controller
{

    public static void index()
    {
        Logger.info("Rendering Trainer Dashboard");
        Trainer trainer = Accounts.getLoggedInTrainer();
        List<Member> memberList = Member.findAll();
        render("dashboardTrainer.html", memberList);
    }

    public static void index2(Long id)
    {
        List<Member> memberList = Member.findAll();
        Member member = null;
        for (int i = 0; i < memberList.size();i++){
            if(memberList.get(i).id == id){
                member = memberList.get(i);
            }
        }
        Logger.info("Rendering Trainer Dashboard");
        Trainer trainer = Accounts.getLoggedInTrainer();
        member.setAssessments(member.getAssessments());
        render("dashboardTrainerMember.html",  member);
    }

    public static void updateMemberAssessmentComment(Long memberId, Long assessmentId, String comment){
        List<Member> memberList = Member.findAll();
        Member member = null;
        for (int i = 0; i < memberList.size();i++){
            if(memberList.get(i).id == memberId){
                member = memberList.get(i);
                Assessment assessment = null;
                for (int j = 0; j < member.getAssessments().size();j++){
                    if(member.getAssessments().get(j).id == assessmentId){
                        assessment = member.getAssessments().get(j);
                        assessment.setComments(comment);

                    }
                }

            }
        }

        member.save();
        index2(memberId);

    }
}