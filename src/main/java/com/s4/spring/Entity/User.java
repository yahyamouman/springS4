package com.s4.spring.Entity;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Date;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    @Email
    private String email;

    @CreationTimestamp
    private Date dateCreation;

    private String address;

    @ColumnDefault("ROLE_USER")
    private String roles="ROLE_USER";

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "owner")
    private Collection<Group> groups;

    @OneToMany(mappedBy = "taggedUser")
    private Collection<UserTag> userTags;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "sender")
    private Collection<Message> sentMessages;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "receiver")
    private Collection<Message> receivedMessages;

}
