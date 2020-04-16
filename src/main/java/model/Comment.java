package model;

public class Comment {

    private int id;
    private Customer customer;
    private Product product;
    private String text;
    private CommentState state;

    public Comment(int id) {
        this.id = id;
    }
}
