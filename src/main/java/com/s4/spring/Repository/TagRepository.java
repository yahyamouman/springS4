package com.s4.spring.Repository;

import com.s4.spring.Entity.Tag;
import com.s4.spring.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    @Query("Select t from Tag t where t.name=?1")
    Tag getTagByName(String name);

    Collection<Tag> getAllByOwner(User owner);
}