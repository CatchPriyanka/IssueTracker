package com.issuetracker.in.Service;


import com.issuetracker.in.Controller.IssueController;
import com.issuetracker.in.DTO.IssueDTO;
import com.issuetracker.in.DTO.IssueResponse;
import com.issuetracker.in.DTO.UserDTO;
import com.issuetracker.in.Exception.IssueTrackerException;
import com.issuetracker.in.Repository.IssueRepositoy;
import com.issuetracker.in.Repository.UserRepository;
import com.issuetracker.in.entity.*;
import com.issuetracker.in.utils.UserConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IssueService {
    Logger logger = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    IssueRepositoy issueRepositoy;

    @Autowired
    UserRepository userRepository;

    public IssueResponse saveIssue(IssueDTO issueDTO) {
        try {
            Issues issue = new Issues();
            issue.setIssueType(issueDTO.getIssueType());
            issue.setTitle(issueDTO.getTitle());
            issue.setDescription(issueDTO.getDescription());
            User assignee = userRepository.findByusername(issueDTO.getAssignee());
            issue.setCreatedBy(assignee);
            User assignedTo= userRepository.findByusername(issueDTO.getAssignedTo());
            issue.setAssignedTo(assignedTo);
            issue.setStatus(Status.valueOf(issueDTO.getStatus()));
            if(issueDTO.getPriority()!=null){
                issue.setPriority(Priority.valueOf(issueDTO.getPriority()));
            }
            if(issueDTO.getEstimatedPoints()>0){
                issue.setEstimatedPoints(issueDTO.getEstimatedPoints());
            }
            issueRepositoy.save(issue);
            return createIssueResponse(issue);
        } catch (IssueTrackerException e) {
            logger.error("Error in saveIssue()::{}",e.getMessage());
            throw new IssueTrackerException(e.getMessage());
        }
    }

    public IssueResponse retrieveIssueById(Long issueId) {
        IssueResponse issueResponse =null;
        try {
            Optional<Issues> issue =issueRepositoy.findById(issueId);
            if(issue.isPresent()){
                issueResponse= createIssueResponse(issue.get());
            }
        } catch (IssueTrackerException e) {
            logger.error("Error in retrieveIssueById()::{}",e.getMessage());
            throw new IssueTrackerException(e.getMessage());
        }
        return issueResponse;
    }

    public Optional<Issues> fetchIssueById(Long issueId){
        try {
            return issueRepositoy.findById(issueId);
        }catch (IssueTrackerException e) {
            logger.error("Error in fetchIssueById()::{}",e.getMessage());
            throw new IssueTrackerException(e.getMessage());
        }

    }


    public static IssueResponse createIssueResponse(Issues issues){
        IssueResponse response = new IssueResponse();
        response.setIssueId(issues.getId());
        response.setIssueType(issues.getIssueType().toString());
        response.setTitle(issues.getTitle());
        response.setDescription(issues.getDescription());
        response.setStatus(issues.getStatus().toString());
        if(null!=issues.getPriority()){
            response.setPriority(issues.getPriority().toString());
        }
        if(issues.getEstimatedPoints() > 0){
            response.setEstimatedPoints(issues.getEstimatedPoints());
        }
        response.setAssignedTo(issues.getAssignedTo().getUsername());
        response.setCreatedBy(issues.getCreatedBy().getUsername());
        response.setEstimatedPoints(issues.getEstimatedPoints());
        return response;

    }



    public List<IssueResponse>retrieveAllIssue() {
        List<IssueResponse> issueResponseList= null;
        try {
            List<Issues>issueList= issueRepositoy.findAll();
            if(!issueList.isEmpty()){
                issueResponseList= issueList.stream().map(IssueService::createIssueResponse).collect(Collectors.toList());
            }

        } catch (IssueTrackerException e) {
            logger.error("Error in retrieveAllIssue()::{}",e.getMessage());
            throw new IssueTrackerException(e.getMessage());
        }
        return issueResponseList;
    }

    public IssueResponse updateIssueById(Optional<Issues> issueDB,IssueDTO issueDTO) {
        IssueResponse issueResponse =new IssueResponse();
        try {
            constructIssueResonse(issueDB, issueDTO);
            issueRepositoy.save(issueDB.get());
            issueResponse= createIssueResponse(issueDB.get());
        } catch (IssueTrackerException e) {
            logger.error("Error in updateIssueById()::{}",e.getMessage());
            throw new IssueTrackerException(e.getMessage());
        }
        return issueResponse;
    }

    private void constructIssueResonse(Optional<Issues> issueDB, IssueDTO issueDTO) {
        if(issueDTO.getTitle()!=null){
            issueDB.get().setTitle(issueDTO.getTitle());
        }
        if(issueDTO.getDescription()!=null){
            issueDB.get().setDescription(issueDTO.getDescription());
        }
        if(issueDTO.getAssignee()!=null) {
            User assignee = userRepository.findByusername(issueDTO.getAssignee());
            issueDB.get().setCreatedBy(assignee);
        }
        if(issueDTO.getAssignedTo()!=null){
            User assignedTO =userRepository.findByusername(issueDTO.getAssignedTo());
            issueDB.get().setAssignedTo(assignedTO);
        }
        if(issueDTO.getStatus()!=null){
            issueDB.get().setStatus(Status.valueOf(issueDTO.getStatus()));
        }
        if(issueDTO.getPriority()!=null){
            issueDB.get().setPriority(Priority.valueOf(issueDTO.getPriority()));
        }
        if(issueDTO.getEstimatedPoints() >0){
            issueDB.get().setEstimatedPoints(issueDTO.getEstimatedPoints());
        }
    }

    public void deleteIssueById(Long issueId){
        try{
            issueRepositoy.deleteById(issueId);

        }catch(IssueTrackerException e){
            logger.error("Error in deleteIssueById()::{}",e.getMessage());
            throw new IssueTrackerException(e.getMessage());
        }

    }

    public List<Issues> fetchEstimatedStories(){
        try{
            List<Issues> storiesList = issueRepositoy.findEstimatedStories();
            return storiesList;
        }catch (IssueTrackerException e){
            logger.error("Error in fetchEstimatedStories()::{}",e.getMessage());
            throw new IssueTrackerException(e.getMessage());
        }
    }
}

