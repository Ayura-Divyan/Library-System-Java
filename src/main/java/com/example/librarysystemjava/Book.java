package com.example.librarysystemjava;

public class Book {
    // Fields
    private String bookId;
    private String isbn;
    private String title;
    private int copies;
    private int availability;
    private double price;

    // Constructor
    public Book(String bookId, String isbn, String title, int copies, int availability, double price) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.copies = copies;
        this.availability = availability;
        this.price = price;
    }

    // Getters
    public String getBookId() {
        return bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public int getCopies() {
        return copies;
    }
    public int getAvailability() {
        return availability;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
