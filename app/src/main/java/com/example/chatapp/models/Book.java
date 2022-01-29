package com.example.chatapp.models;

public class Book {
    private String title, author, publisher, key;
    private int year, noofcopies;
    private double cost;

    public Book(String title,String author,int year, int noofcopies, String publisher,double cost)
    {   this.title=title;
        this.author=author;
        this.year=year;
        this.publisher=publisher;
        this.key = "";
        this.cost=cost;
        this.noofcopies = noofcopies;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public int getNoofcopies() { return noofcopies; }
    public String getPublisher() { return publisher; }
    public double getCost() { return cost; }
    public String getKey() { return key; }

    public void setTitle(String title) { this.title=title; }
    public void setAuthor(String author) { this.author=author; }
    public void setYear(int year) { this.year=year; }
    public void setNoofcopies(int noofcopies) { this.noofcopies=noofcopies; }
    public void setPublisher(String publisher) { this.publisher=publisher; }
    public void setCost(double cost) { this.cost=cost; }
    public void setKey(String key) { this.key = key; }

    public String toString() {
        return "The details of the book are: " + title + ", " + author + ", " + year + ", " +
                noofcopies + ", " + publisher + ", " + cost + ", " + key;
    }
}
