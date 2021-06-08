package com.s4.spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User taggedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Tag usedTag;
}
