package me.kowo.opencity.models;



public class UserFeedback {
    private Integer id;
    private String email;
    private String comment;

    public UserFeedback(String email, String comment) {
        this.email = email;
        this.comment = comment;
    }
    public String getEmail() {
        return email;
    }

    public String getComment() {
        return comment;
    }

    public Integer getId() {
        return id;
    }
}
