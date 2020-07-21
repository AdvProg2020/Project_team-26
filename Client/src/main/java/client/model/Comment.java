package client.model;

import client.model.enums.CommentState;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {
    private int id;
    private String title;
    private String text;
    private Boolean hasBought;
    private User customer;
    private CommentState state;

    @JsonCreator
    public Comment(@JsonProperty("id") int id,
                   @JsonProperty("title") String title,
                   @JsonProperty("text") String text,
                   @JsonProperty("hasBought") Boolean hasBought,
                   @JsonProperty("customer") User customer,
                   @JsonProperty("state") CommentState state) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.hasBought = hasBought;
        this.customer = customer;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Boolean getHasBought() {
        return hasBought;
    }

    public User getCustomer() {
        return customer;
    }

    public CommentState getState() {
        return state;
    }
}
