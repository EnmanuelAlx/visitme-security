package gil.mota.visitme.visitmesecurity.models;

import gil.mota.visitme.visitmesecurity.utils.Functions;

/**
 * Created by mota on 18/4/2018.
 */

public class Alert {
    private String id;
    private String type;
    private String message;
    private String description;
    private Community community;
    private User author;
    private String hour;
    private String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type.toUpperCase();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                ", community=" + community +
                ", author=" + author +
                ", hour='" + hour + '\'' +
                '}';
    }

    public String getCreated_at() {
        return Functions.formatDate(created_at);
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
