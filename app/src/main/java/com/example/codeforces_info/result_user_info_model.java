package com.example.codeforces_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class result_user_info_model {

    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("lastOnlineTimeSeconds")
    @Expose
    private Integer lastOnlineTimeSeconds;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("friendOfCount")
    @Expose
    private Integer friendOfCount;
    @SerializedName("titlePhoto")
    @Expose
    private String titlePhoto;
    @SerializedName("handle")
    @Expose
    private String handle;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("contribution")
    @Expose
    private Integer contribution;
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("maxRating")
    @Expose
    private Integer maxRating;
    @SerializedName("registrationTimeSeconds")
    @Expose
    private Integer registrationTimeSeconds;
    @SerializedName("maxRank")
    @Expose
    private String maxRank;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getLastOnlineTimeSeconds() {
        return lastOnlineTimeSeconds;
    }

    public void setLastOnlineTimeSeconds(Integer lastOnlineTimeSeconds) {
        this.lastOnlineTimeSeconds = lastOnlineTimeSeconds;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getFriendOfCount() {
        return friendOfCount;
    }

    public void setFriendOfCount(Integer friendOfCount) {
        this.friendOfCount = friendOfCount;
    }

    public String getTitlePhoto() {
        return titlePhoto;
    }

    public void setTitlePhoto(String titlePhoto) {
        this.titlePhoto = titlePhoto;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(Integer maxRating) {
        this.maxRating = maxRating;
    }

    public Integer getRegistrationTimeSeconds() {
        return registrationTimeSeconds;
    }

    public void setRegistrationTimeSeconds(Integer registrationTimeSeconds) {
        this.registrationTimeSeconds = registrationTimeSeconds;
    }

    public String getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(String maxRank) {
        this.maxRank = maxRank;
    }
}
