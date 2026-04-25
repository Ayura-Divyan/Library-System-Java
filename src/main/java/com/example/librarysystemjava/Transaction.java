package com.example.librarysystemjava;

public class Transaction {
    // Fields
    private String transactionId;
    private String date;
    private String bookId;
    private String studentId;
    private int type;

    // Constructor
    public Transaction(String transactionId, String date, String bookId, String studentId, int type) {
        this.transactionId = transactionId;
        this.date = date;
        this.bookId = bookId;
        this.studentId = studentId;
        this.type = type;
    }

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public String getDate() {
        return date;
    }

    public String getBookId() {
        return bookId;
    }
    public String getStudentId() {
        return studentId;
    }

    public int getType() {
        return type;
    }

    // Setters
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setType(int type) {
        this.type = type;
    }
}
