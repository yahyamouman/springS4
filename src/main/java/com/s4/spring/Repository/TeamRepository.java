package com.s4.spring.Repository;

import com.s4.spring.Entity.Group;
import com.s4.spring.Entity.Tag;
import com.s4.spring.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

    @Query("Select t from Team t where t.name=?1")
    Team getTeamByName(String name);

}