package com.example.codeforces_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Author {

    @SerializedName("contestId")
    @Expose
    private Integer contestId;
    @SerializedName("members")
    @Expose
    private List<Member> members = null;
    @SerializedName("participantType")
    @Expose
    private String participantType;
    @SerializedName("ghost")
    @Expose
    private Boolean ghost;
    @SerializedName("startTimeSeconds")
    @Expose
    private Integer startTimeSeconds;

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public String getParticipantType() {
        return participantType;
    }

    public void setParticipantType(String participantType) {
        this.participantType = participantType;
    }

    public Boolean getGhost() {
        return ghost;
    }

    public void setGhost(Boolean ghost) {
        this.ghost = ghost;
    }

    public Integer getStartTimeSeconds() {
        return startTimeSeconds;
    }

    public void setStartTimeSeconds(Integer startTimeSeconds) {
        this.startTimeSeconds = startTimeSeconds;
    }

}
