package com.s4.spring.Services;

import com.s4.spring.Entity.Tag;
import com.s4.spring.Entity.User;
import com.s4.spring.Entity.UserTag;
import com.s4.spring.Repository.TagRepository;
import com.s4.spring.Repository.UserRepository;
import com.s4.spring.Repository.UserTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class TagServices {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserTagRepository userTagRepository;
    public Collection<Tag> getUserTags(long userId){
        User user = userRepository.findById(userId).get();
        Collection<Tag> tags = new ArrayList<>();
        for(UserTag userTag : user.getUserTags()){
            tags.add(userTag.getUsedTag());
        }
        return tags;
    }

    public void addUserToTag(long userId, long tagId){
        User user = userRepository.findById(userId).get();
        Tag tag = tagRepository.findById(tagId).get();
        UserTag userTag = new UserTag();
        userTag.setTaggedUser(user);
        userTag.setUsedTag(tag);
        userTagRepository.saveAndFlush(userTag);
    }

    public Collection<Tag> getOwnedTags(long userId){
        User user = userRepository.findById(userId).get();
        return tagRepository.getAllByOwner(user);
    }

}
