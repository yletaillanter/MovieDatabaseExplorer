package com.yoannlt.mde.moviedatabaseexplorer.model;

/**
 * Created by yoannlt on 22/06/2016.
 */
public class CrewPerson {

    private String credit_id;
    private String department;
    private int id;
    private String job;
    private String name;
    private String profile_path;

    public CrewPerson() {
    }

    public CrewPerson(String credit_id, String department, int id, String job, String name, String profile_path) {
        this.credit_id = credit_id;
        this.department = department;
        this.id = id;
        this.job = job;
        this.name = name;
        this.profile_path = profile_path;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    @Override
    public String toString() {
        return "CrewPerson{" +
                "credit_id='" + credit_id + '\'' +
                ", department='" + department + '\'' +
                ", id=" + id +
                ", job='" + job + '\'' +
                ", name='" + name + '\'' +
                ", profile_path='" + profile_path + '\'' +
                '}';
    }
}
