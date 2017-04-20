package me.kowo.opencity.models;


public class Category {

    /**
     * id : 1
     * name : загальні
     * comment : Загальний навчальний заклад
     * created_at : 2016-12-26 05:30:44
     * updated_at : 2017-01-04 11:32:59
     */

    private int id;
    private String name;
    private String comment;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
