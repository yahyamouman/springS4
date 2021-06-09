package com.s4.spring.Controller;

import com.s4.spring.Entity.Group;
import com.s4.spring.Entity.User;
import com.s4.spring.Repository.*;
import com.s4.spring.Services.TagServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String register(Authentication authentication){
        return "register";
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String register(User user){
        user.setUsername(user.getEmail());
        userRepository.save(user);
        userRepository.flush();
        Group group = new Group();
        group.setName("Default");
        group.setOwner(user);
        group.setDescription("This is the default group");
        groupRepository.saveAndFlush(group);


        return "redirect:/login";
    }
}
