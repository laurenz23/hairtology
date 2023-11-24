package com.project.hairtologyowner.models;

public class ShopReview {

    private int stars;
    private String feedback;
    private String userId;
    private String email;
    private String firstName;
    private String lastName;

    public ShopReview() {}

    public ShopReview(int stars, String feedback, String userId, String email, String firstName, String lastName) {
        this.stars = stars;
        this.feedback = feedback;
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String description) {
        this.feedback = description;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
