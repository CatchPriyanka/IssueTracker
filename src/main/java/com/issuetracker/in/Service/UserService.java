package com.issuetracker.in.Service;

import com.issuetracker.in.Controller.IssueController;
import com.issuetracker.in.DTO.UserDTO;
import com.issuetracker.in.Exception.IssueTrackerException;
import com.issuetracker.in.Repository.UserRepository;
import com.issuetracker.in.entity.Role;
import com.issuetracker.in.entity.User;
import com.issuetracker.in.utils.UserConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleService roleService;


    public User saveUser(UserDTO userDTO) {
        try {
            User user = new User();
            user.setUsername(userDTO.getUserName());
            user.setEmail(userDTO.getEmail());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setPassword(userDTO.getPassword());
            Role role = roleService.findByRoleName(userDTO.getRoleName());
            if (!(role == null)) {
                user.setRole(role);
                userRepo.save(user);
            } else {
                throw new IssueTrackerException(UserConstants.ROLE_NOT_AVAIL);
            }
            return user;
        } catch (IssueTrackerException e) {
            logger.error("Error in saveUser()::{}", e.getMessage());
            throw new IssueTrackerException(e.getMessage());
        }
    }


    public Optional<User> retrieveUser(Long userId) {
        try {
            return userRepo.findById(userId);
        } catch (IssueTrackerException e) {
            logger.error("Error in retrieveUser()::{}", e.getMessage());
            throw new IssueTrackerException(e.getMessage());
        }
    }


    public Optional<User> updateUser(UserDTO userDTO, Optional<User> user) {
        try {
            user.get().setFirstName(userDTO.getFirstName());
            user.get().setLastName(userDTO.getLastName());
            user.get().setPassword(userDTO.getPassword());
            user.get().setEmail(userDTO.getEmail());
            Role role = roleService.findByRoleName(userDTO.getRoleName());
            if (!(role == null)) {
                user.get().setRole(role);
            } else {
                throw new IssueTrackerException(UserConstants.ROLE_NOT_AVAIL);
            }
            userRepo.save(user.get());
        } catch (IssueTrackerException e) {
            logger.error("Error in updateUser()::{}", e.getMessage());
            throw new IssueTrackerException(e.getMessage());
        }
        return user;
    }

    public void deleteUser(Long userId) {
        try {
            userRepo.deleteById(userId);
        } catch (IssueTrackerException e) {
            logger.error("Error in deleteUser()::{}", e.getMessage());
            throw new IssueTrackerException(e.getMessage());
        }

    }
}
