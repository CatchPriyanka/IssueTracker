package com.issuetracker.in.Service;

import com.issuetracker.in.Controller.IssueController;
import com.issuetracker.in.DTO.Plan;
import com.issuetracker.in.Exception.IssueTrackerException;
import com.issuetracker.in.Repository.UserRepository;
import com.issuetracker.in.entity.Issues;
import com.issuetracker.in.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {

    Logger logger = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    UserRepository userRepository;

    public List<Plan> planStories(List<Issues> storyList){
        try {
            List<User> users = userRepository.findActiveUsers();
            List<Plan> planList = getPlan(users, storyList);
            return planList;
        }catch (IssueTrackerException ex){
            throw new IssueTrackerException(ex.getMessage());
        }
    }

    public List<Plan> getPlan(List<User> activeUsers,List<Issues> storyList){
        int weekCounter =1;
        List<Plan> planList=new ArrayList<>();
        if(activeUsers.size() > 0) {
            while (storyList.size() > 0) {
                for (int i = 0; i < activeUsers.size(); i++) {
                    int BW = 10;
                    for (int j = 0; j < storyList.size(); j++) {
                        if (BW == storyList.get(j).getEstimatedPoints()) {
                            Plan plan =new Plan();
                            plan.setEmail(activeUsers.get(i).getEmail());
                            plan.setStoryId((storyList.get(j).getId()).toString());
                            plan.setEstimatedPoints(storyList.get(j).getEstimatedPoints());
                            plan.setWeekPlannedFor(weekCounter);
                            planList.add(plan);
                            BW = 0;
                            storyList.remove(j);
                        } else if (BW == 0) {
                            break;
                        } else if (BW > storyList.get(j).getEstimatedPoints()) {
                            Plan plan =new Plan();
                            plan.setEmail(activeUsers.get(i).getEmail());
                            plan.setStoryId((storyList.get(j).getId()).toString());
                            plan.setWeekPlannedFor(weekCounter);
                            plan.setEstimatedPoints(storyList.get(j).getEstimatedPoints());
                            planList.add(plan);
                            int RP = BW - (storyList.get(j).getEstimatedPoints());
                            storyList.remove(j);
                            BW = RP;
                            if (BW > 0) {
                                j--;
                                continue;
                            }
                            break;
                        } else if (BW < storyList.get(j).getEstimatedPoints()) {
                            break;
                        }
                    }

                }
                weekCounter++;
            }
        }else{
            logger.info("No developer Available");
        }
        return planList;
    }

}
