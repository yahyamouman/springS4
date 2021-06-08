package com.s4.spring.Repository;

import com.s4.spring.Entity.Tag;
import com.s4.spring.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    Collection<Tag> getAllByOwner(User owner);
}