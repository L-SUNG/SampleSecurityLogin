package com.example.SampleSecurityLogin.domain.member;

import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(length = 50, unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String pass;
}
