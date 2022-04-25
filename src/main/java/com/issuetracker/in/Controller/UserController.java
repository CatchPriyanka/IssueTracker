package com.issuetracker.in.Controller;


import com.issuetracker.in.DTO.UserDTO;
import com.issuetracker.in.Exception.IssueTrackerException;
import com.issuetracker.in.Model.ResponseBody;
import com.issuetracker.in.Service.UserService;
import com.issuetracker.in.entity.User;
import com.issuetracker.in.utils.UserConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<ResponseBody> adduser(@RequestBody UserDTO userDTO) {
        ResponseBody responseMessage = new ResponseBody();
        try {
            User user = userService.saveUser(userDTO);
            responseMessage.setMessage(UserConstants.USER_ADDED);
            responseMessage.setResponse(user);
            responseMessage.setCode(UserConstants.SUCCESS);

            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
        } catch (IssueTrackerException e) {
            if (e.getMessage().endsWith(UserConstants.ROLE_NOT_AVAIL)) {
                responseMessage.setCode(UserConstants.SUCCESS);
                logger.error("Exception due to{}::",e.getMessage().toString());
                responseMessage.setMessage(UserConstants.ROLE_NOT_AVAIL);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            } else {
                responseMessage.setCode(UserConstants.FAILURE);
                logger.error("Exception due to{}::",e.getMessage().toString());
                responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
                return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/retrieveUser/{userId}")
    public ResponseEntity<ResponseBody> retrieveUser(@PathVariable int userId) {
        ResponseBody responseMessage = new ResponseBody();
        try {
            Optional<User> user = userService.retrieveUser((long) userId);
            if (user.isPresent()) {
                responseMessage.setMessage(UserConstants.USER_RETRIEVED);
                responseMessage.setResponse(user);
            } else {
                responseMessage.setMessage(UserConstants.USER_NOT_AVAIL);
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

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<ResponseBody> updateUser(@PathVariable long userId, @RequestBody UserDTO userDTO) {
        ResponseBody responseMessage = new ResponseBody();
        try {
            Optional<User> user = userService.retrieveUser((userId));
            if (user.isPresent()) {
                Optional<User> updatedUser = userService.updateUser(userDTO, user);
                responseMessage.setMessage(UserConstants.USER_UPDATED);
                responseMessage.setResponse(updatedUser);
            } else {
                responseMessage.setMessage(UserConstants.USER_NOT_AVAIL);
            }
            responseMessage.setCode(UserConstants.SUCCESS);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (IssueTrackerException e) {
            if (e.getMessage().endsWith(UserConstants.ROLE_NOT_AVAIL)) {
                responseMessage.setCode(UserConstants.SUCCESS);
                logger.error("Exception due to{}::",e.getMessage().toString());
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            } else {
                responseMessage.setCode(UserConstants.FAILURE);
                logger.error("Exception due to{}::",e.getMessage().toString());
                responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
                return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            responseMessage.setCode(UserConstants.FAILURE);
            logger.error("Exception due to{}::",e.getMessage().toString());
            responseMessage.setMessage(UserConstants.SERVICE_UNAVAILABLE);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ResponseBody> deleteUser(@PathVariable long userId) {
        ResponseBody responseMessage = new ResponseBody();
        try {
            Optional<User> user = userService.retrieveUser(userId);
            if (user.isPresent()) {
                userService.deleteUser(userId);
                responseMessage.setMessage(UserConstants.USER_DELETED);
                responseMessage.setResponse(user);
            } else {
                responseMessage.setMessage(UserConstants.USER_NOT_AVAIL);
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
}
