package com.note11.projectschoolall.model;

public class UserModel {
    private String name, schoolName, schoolCode, phoneNumber, grade, classNum, class2Num;

    public UserModel(){}

    public UserModel(String name, String schoolName, String schoolCode, String phoneNumber, String grade, String classNum, String class2Num) {
        this.name = name;
        this.schoolName = schoolName;
        this.schoolCode = schoolCode;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.classNum = classNum;
        this.class2Num = class2Num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getClass2Num() {
        return class2Num;
    }

    public void setClass2Num(String class2Num) {
        this.class2Num = class2Num;
    }
}
