package com.example.librarysystemjava;

public class Student {
    // Fields
    private String studentId;
    private String firstName;

    // Constructor
    public Student(String studentId, String firstName) {
        this.studentId = studentId;
        this.firstName = firstName;
    }

    // Getters
    public String getStudentId() {
        return studentId;
    }
    public String getFirstName() {
        return firstName;
    }

    // Setters
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
