package com.s4.spring.Controller;

import com.s4.spring.Entity.Group;
import com.s4.spring.Entity.Team;
import com.s4.spring.Entity.User;
import com.s4.spring.Repository.*;
import com.s4.spring.Security.entity.MyUserDetails;
import com.s4.spring.Services.TagServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class TeamController {
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value =  "/deleteTeam")
    public String deleteTeam(Authentication authentication, Model model, Team team){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        Team newTeam = teamRepository.getTeamByName(team.getName());
        teamRepository.delete(newTeam);
        teamRepository.flush();

        model.addAttribute("user",user);
        model.addAttribute("teams",user.getTeams());
        return "showTeams";
    }

    @RequestMapping(value = "/showTeams")
    public String showTeam(Authentication authentication, Model model){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        model.addAttribute("user",user);
        model.addAttribute("teams",user.getTeams());
        return "showTeams";
    }

    @RequestMapping(value =  "/CreateTeam")
    public String team(Authentication authentication, Model model){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();

        model.addAttribute("user",user);
        return "NewTeam";
    }

    @RequestMapping(value =  "/addTeam")
    public String addTeam(Authentication authentication, Model model, Team team){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        Team newTeam = new Team();
        newTeam.setName(team.getName());
        newTeam.setOwner(user);
        Collection<Group> groups = user.getGroups();
        newTeam.setMyGroup((Group) groups.toArray()[0]);
        teamRepository.saveAndFlush(newTeam);

        model.addAttribute("user",user);
        return "NewTeam";
    }
}
