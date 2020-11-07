package com.note11.projectschoolall.model;

public class TodoModel {

    String classNumber, date, title, contentData, subject;

    public TodoModel(){}

    public TodoModel(String classNumber, String date, String title, String contentData, String subject) {
        this.classNumber = classNumber;
        this.date = date;
        this.title = title;
        this.contentData = contentData;
        this.subject = subject;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentData() {
        return contentData;
    }

    public void setContentData(String contentData) {
        this.contentData = contentData;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
