package com.example.teacherwanted.bbsdiscuss.dto;

public class Response {
    private String memAccount;
    private String memNickname;
    private byte[] memPhoto;
    private Integer favStatus;
    private Integer reaction_status;
    private Integer bbsPostId;

    public String getMemAccount() {
        return memAccount;
    }

    public void setMemAccount(String memAccount) {
        this.memAccount = memAccount;
    }

    public String getMemNickname() {
        return memNickname;
    }

    public void setMemNickname(String memNickname) {
        this.memNickname = memNickname;
    }

    public byte[] getMemPhoto() {
        return memPhoto;
    }

    public void setMemPhoto(byte[] memPhoto) {
        this.memPhoto = memPhoto;
    }

    public Integer getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(Integer favStatus) {
        this.favStatus = favStatus;
    }

    public Integer getReaction_status() {
        return reaction_status;
    }

    public void setReaction_status(Integer reaction_status) {
        this.reaction_status = reaction_status;
    }

    public Integer getBbsPostId() {
        return bbsPostId;
    }

    public void setBbsPostId(Integer bbsPostId) {
        this.bbsPostId = bbsPostId;
    }
}
