package com.s4.spring.Controller;

import com.s4.spring.Entity.User;
import com.s4.spring.Repository.UserRepository;
import com.s4.spring.Security.entity.MyUserDetails;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.Authenticator;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/app")
    public String index(Authentication authentication, Model model){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        model.addAttribute("user",user);
        return "home";
    }

    /*
    @RequestMapping("/adduser")
    @ResponseBody
    public User addUser(Authentication authentication){
        User user = new User();
        user.setEmail("faleh@gmail.com");
        user.setUsername("faleh");
        user.setPassword("azerty");
        userRepository.save(user);
        userRepository.flush();

        return user;
    }*/

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String register(User user){
        user.setUsername(user.getEmail());
        userRepository.save(user);
        userRepository.flush();

        return "redirect:/login";
    }

    @RequestMapping(value = "/")
    public String home(Authentication authentication, Model model){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        model.addAttribute("user",user);
        return "index";
    }

    @GetMapping("/register")
    public String register(Authentication authentication){
        return "register";
    }
}
