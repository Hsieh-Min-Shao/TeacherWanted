package com.example.teacherwanted.wish.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "wishnew")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id", insertable = false)
    private Integer wishId;

    @Column(name = "mem_account")
    private String memAccount;

    @Column(name = "wish_subject")
    private String wishSubject;

    @Column(name = "wish_location")
    private String wishLocation;
    @Column(name = "wish_student")
    private String wishStudents;
    @Column(name = "wish_period")
    private String wishPeriod;
    @Column(name = "wish_salary")
    private String wishSalary;
    @Column(name = "wish_content", columnDefinition = "TEXT")
    private String wishContent;
    @Column(name = "wish_email")
    private String wishEmail;
    @Column(name = "create_time", insertable = false)
    private Timestamp createTime;
    @Column(name = "update_time", insertable = false)
    private Timestamp updateTime;
    @Column(name = "wish_status", insertable = false)
    private Integer WishStatus;



}
