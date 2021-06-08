package com.s4.spring.Repository;

import com.s4.spring.Entity.Group;
import com.s4.spring.Entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTagRepository extends JpaRepository<UserTag,Long> {

}