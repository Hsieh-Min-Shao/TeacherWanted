package com.example.teacherwanted.register_login.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="MEMBER")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "mem_id")
    private Integer memId;
    @Column(name = "mem_account")
    public String memAccount;
    @Column(name = "mem_password")
    private String memPassword;
    @Column(name = "mem_name")
    private String memName;
    @Column(name = "mem_phone")
    private String memPhone;
    @Column(name = "mem_nickname")
    private String memNickname;
    @Column(name = "mem_birthday")
    private Date memBirthday;
    @Column(name = "mem_gender")
    private Integer memGender;
    @Column(name = "mem_email")
    private String memEmail;
    @Column(name = "mail_verify", insertable = false)
    private Integer mailVerify;
    @Column(name = "mem_location")
    private String memLocation;
    @Column(name = "mem_photo")
    private byte[] memPhoto;
    private Integer interest1;
    private Integer interest2;
    private Integer interest3;
    @Column(name = "create_time", insertable = false)
    private Timestamp createTime;
    @Column(name = "update_time", insertable = false)
    private Timestamp updateTime;
    @Column(name = "mem_status", insertable = false)
    private Integer memStatus;
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
//    private boolean rememberMe;
//    public boolean isRememberMe() {
//        this.rememberMe = true;
//        return rememberMe;
//    }



}
