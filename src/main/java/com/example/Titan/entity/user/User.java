package com.example.Titan.entity.user;

import com.example.Titan.entity.LongBasid;
import com.example.Titan.entity.watches.Watches;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends LongBasid {
    @Column(name = "email")
    private String email;

    @Column(name = "uid")
    private String uid;

    @Column(name = "mobile")
    private String mobile;

//    @Column(name = "fav_watch")
//    @ElementCollection(fetch = FetchType.EAGER)
//    private List<Watches> favourateWatch = new ArrayList<>();

    @Column(name ="password")
    private  String password;

    @Column(name = "name")
    private String name;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "role")
    private Long role;

    @Column(name = "is_blacklisted")
    private Boolean isBlacklisted = false;

}