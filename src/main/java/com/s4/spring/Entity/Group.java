package com.s4.spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name="group_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long groupId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String description;

    @CreationTimestamp
    private Date dateCreation;

    @OneToMany(mappedBy = "myGroup",fetch = FetchType.LAZY)
    Collection<Team> teams;
}
