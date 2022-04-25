package com.issuetracker.in.Controller;


import com.issuetracker.in.DTO.IssueDTO;
import com.issuetracker.in.DTO.IssueResponse;
import com.issuetracker.in.DTO.Plan;
import com.issuetracker.in.Exception.IssueTrackerException;
import com.issuetracker.in.Model.ResponseBody;
import com.issuetracker.in.Service.IssueService;
import com.issuetracker.in.Service.PlanService;
import com.issuetracker.in.entity.Issues;
import com.issuetracker.in.entity.User;
import com.issuetracker.in.utils.UserConstants;
import com.sun.net.httpserver.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/issues")
public class IssueController {
    Logger logger = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    IssueService issueService;

    @Autowired
    PlanService planService;

    @PostMapping("/addIssue")
    public ResponseEntity<ResponseBody> addIssue(@RequestBody IssueDTO issueDTO) {
        ResponseBody responseMessage = new ResponseBody();
        try {
            IssueResponse issue = issueService.saveIssue(issueDTO);
            responseMessage.setMessage(UserConstants.ISSUE_ADDED);
            responseMessage.setResponse(issue);
            responseMessage.setCode(UserConstants.SUCCESS);
            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
        } catch (IssueTrackerException e) {
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/retrieveIssues/{issueId}")
    public ResponseEntity<ResponseBody> retrieveIssueById(@PathVariable long issueId) {
        ResponseBody responseMessage = new ResponseBody();
        try {
            IssueResponse issueResponse = issueService.retrieveIssueById(issueId);
            if (null != issueResponse) {
                responseMessage.setMessage(UserConstants.ISSUE_RETRIEVED);
                responseMessage.setResponse(issueResponse);
            } else {
                responseMessage.setMessage(UserConstants.ISSUE_NOT_AVAIL);
            }
            responseMessage.setCode(UserConstants.SUCCESS);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (IssueTrackerException e) {
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * finding all the issues
     */
    @GetMapping("/retrieveAllIssues")
    public ResponseEntity<ResponseBody> retrieveAllIssues() {
        ResponseBody responseMessage = new ResponseBody();
        try {
            List<IssueResponse> issueList = issueService.retrieveAllIssue();
            if (!issueList.isEmpty()) {
                responseMessage.setMessage(UserConstants.ISSUE_RETRIEVED);
                responseMessage.setResponse(issueList);
            } else {
                responseMessage.setMessage(UserConstants.NO_ISSUE_FOUND);
            }
            responseMessage.setCode(UserConstants.SUCCESS);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (IssueTrackerException e) {
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateIssue/{issueId}")
    public ResponseEntity<ResponseBody> updateUser(@PathVariable long issueId, @RequestBody IssueDTO issueDTO) {
        ResponseBody responseMessage = new ResponseBody();
        try {
            Optional<Issues> issue = issueService.fetchIssueById((issueId));
            if (issue.isPresent()) {
                IssueResponse updateIssueDetails= issueService.updateIssueById(issue, issueDTO);
                responseMessage.setMessage(UserConstants.USER_UPDATED);
                responseMessage.setResponse(updateIssueDetails);
            } else {
                responseMessage.setMessage(UserConstants.ISSUE_NOT_AVAIL);
            }
            responseMessage.setCode(UserConstants.SUCCESS);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (IssueTrackerException e) {
            if (e.getMessage().endsWith(UserConstants.ROLE_NOT_AVAIL)) {
                responseMessage.setCode(UserConstants.SUCCESS);
                logger.error("Exception due to{}::",e.getMessage().toString());
                responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            } else {
                responseMessage.setCode(UserConstants.FAILURE);
                responseMessage.setMessage(e.getMessage().toString());
                return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteIssue/{issueId}")
    public ResponseEntity<ResponseBody> deleteIssueById(@PathVariable long issueId) {
        ResponseBody responseMessage = new ResponseBody();
        try {
            Optional<Issues> issues = issueService.fetchIssueById(issueId);
            if (issues.isPresent()) {
                issueService.deleteIssueById(issueId);
                responseMessage.setMessage(UserConstants.ISSUE_DELETED);
                responseMessage.setResponse(issues);
            } else {
                responseMessage.setMessage(UserConstants.ISSUE_NOT_AVAIL);
            }
            responseMessage.setCode(UserConstants.SUCCESS);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (IssueTrackerException e) {
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPlan")
    public ResponseEntity<ResponseBody> getStoryPlan(){
        ResponseBody responseMessage = new ResponseBody();
        try {
            List<Issues> stories = issueService.fetchEstimatedStories();
            List<Plan> planStories = planService.planStories(stories);
            if(planStories.size() >0) {
                responseMessage.setMessage("Plans Retrieved from the System Successfully");
                responseMessage.setResponse(planStories);
            }else{
                responseMessage.setMessage("No Plans Available in the System");
            }
            responseMessage.setCode(UserConstants.SUCCESS);
            logger.info("responseMessage {}:",responseMessage.getResponse());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }catch (Exception e){
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
