package com.s4.spring.Controller;

import com.s4.spring.Entity.*;
import com.s4.spring.Repository.*;
import com.s4.spring.Security.entity.MyUserDetails;
import com.s4.spring.Services.TagServices;
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
import java.util.ArrayList;
import java.util.Collection;

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

    @Autowired
    GroupRepository groupRepository;

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

    @Autowired
    TagServices tagServices;

    @RequestMapping(value = "/CreateTags")
    public String createTag(Authentication authentication, Model model){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        model.addAttribute("user",user);
        model.addAttribute("tags",user.getOwnedTags());
        return "createTag";
    }

    @Autowired
    TagRepository tagRepository;
    @Autowired
    UserTagRepository userTagRepository;

    @RequestMapping(value = "/tag")
    @ResponseBody
    public Tag[] tagRandom(Authentication authentication, Model model) {
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        Tag tag = new Tag();
        tag.setName("GL");
        tag.setOwner(user);
        tagRepository.save(tag);
        Tag tag2 = new Tag();
        tag2.setName("GL");
        tag2.setOwner(user);
        tag2 = tagRepository.save(tag2);
        return new Tag[]{tag, tag2};
    }


    @RequestMapping(value = "/DeleteMember")
    public String createTag(Model model, Authentication authentication, @RequestAttribute(name="tagId") long tagId, @RequestAttribute(name="userId") long userId){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        Tag tag = tagRepository.findById(tagId).get();
        UserTag userTag = userTagRepository.findUserTagByTaggedUserAndUsedTag(user,tag);
        if(tag.getOwner().getUserId()==(user.getUserId())){
            userTagRepository.delete(userTag);
            userTagRepository.flush();
        }

        model.addAttribute("user",user);
        model.addAttribute("tag",tag);
        model.addAttribute("members",tagServices.getTagMembers(tagId));
        return "modifyTag";
    }

    @RequestMapping(value = "/ModifyTags/{tagId}")
    public String modifyTag(Authentication authentication, Model model,@RequestParam(name="tagId") String tagId){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        Tag tag = tagRepository.findById(Long.parseLong(tagId)).get();
        model.addAttribute("user",user);
        model.addAttribute("tag",tag);
        model.addAttribute("members",tagServices.getTagMembers(Long.parseLong(tagId)));
        return "modifyTag";
    }

    @RequestMapping(value =  "/addTag")
    public String addTag(Authentication authentication, Model model, Tag tag){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        Tag newTag = new Tag();
        newTag.setName(tag.getName());
        newTag.setOwner(user);
        tagRepository.saveAndFlush(newTag);

        model.addAttribute("user",user);
        model.addAttribute("tags",user.getOwnedTags());
        return "createTag";
    }

    @Autowired
    TeamRepository teamRepository;

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

    @RequestMapping(value =  "/Groups")
    public String group(Authentication authentication, Model model){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();

        model.addAttribute("user",user);
        return "NewGroup";
    }

    @RequestMapping(value =  "/deleteTag")
    public String deleteTag(Authentication authentication, Model model, Tag tag){
        MyUserDetails principal = (MyUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(principal.getUserId()).get();
        Tag newTag = tagRepository.getTagByName(tag.getName());
        tagRepository.delete(newTag);
        tagRepository.flush();

        model.addAttribute("user",user);
        model.addAttribute("tags",user.getOwnedTags());
        return "createTag";
    }

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

}
