package com.s4.spring.Controller;

import com.s4.spring.Entity.Group;
import com.s4.spring.Entity.User;
import com.s4.spring.Repository.*;
import com.s4.spring.Security.entity.MyUserDetails;
import com.s4.spring.Services.TagServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GroupController {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value =  "/Groups")
    public String group(Authentication authentication, Model model){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();

        model.addAttribute("user",user);
        return "NewGroup";
    }

    @RequestMapping(value =  "/addGroup")
    public String addGroup(Authentication authentication, Model model, Group group){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        Group newGroup = new Group();
        newGroup.setName(group.getName());
        newGroup.setOwner(user);
        groupRepository.saveAndFlush(group);

        model.addAttribute("user",user);
        return "NewGroup";
    }
}
