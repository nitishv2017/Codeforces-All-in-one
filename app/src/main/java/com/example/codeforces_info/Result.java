package com.example.codeforces_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("contestId")
    @Expose
    private Integer contestId;
    @SerializedName("creationTimeSeconds")
    @Expose
    private Integer creationTimeSeconds;
    @SerializedName("relativeTimeSeconds")
    @Expose
    private Integer relativeTimeSeconds;
    @SerializedName("problem")
    @Expose
    private Problem problem;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("programmingLanguage")
    @Expose
    private String programmingLanguage;
    @SerializedName("verdict")
    @Expose
    private String verdict;
    @SerializedName("testset")
    @Expose
    private String testset;
    @SerializedName("passedTestCount")
    @Expose
    private Integer passedTestCount;
    @SerializedName("timeConsumedMillis")
    @Expose
    private Integer timeConsumedMillis;
    @SerializedName("memoryConsumedBytes")
    @Expose
    private Integer memoryConsumedBytes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getCreationTimeSeconds() {
        return creationTimeSeconds;
    }

    public void setCreationTimeSeconds(Integer creationTimeSeconds) {
        this.creationTimeSeconds = creationTimeSeconds;
    }

    public Integer getRelativeTimeSeconds() {
        return relativeTimeSeconds;
    }

    public void setRelativeTimeSeconds(Integer relativeTimeSeconds) {
        this.relativeTimeSeconds = relativeTimeSeconds;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public String getTestset() {
        return testset;
    }

    public void setTestset(String testset) {
        this.testset = testset;
    }

    public Integer getPassedTestCount() {
        return passedTestCount;
    }

    public void setPassedTestCount(Integer passedTestCount) {
        this.passedTestCount = passedTestCount;
    }

    public Integer getTimeConsumedMillis() {
        return timeConsumedMillis;
    }

    public void setTimeConsumedMillis(Integer timeConsumedMillis) {
        this.timeConsumedMillis = timeConsumedMillis;
    }

    public Integer getMemoryConsumedBytes() {
        return memoryConsumedBytes;
    }

    public void setMemoryConsumedBytes(Integer memoryConsumedBytes) {
        this.memoryConsumedBytes = memoryConsumedBytes;
    }

}
