package model;

public class Comment implements Savable {

    private int id;
    private boolean isLoaded;
    private Customer customer;
    private Product product;
    private String text;
    private CommentState state;

    public Comment(int id) {
        this.id = id;
    }

    @Override
    public void load() {
        if(!isLoaded) {

            isLoaded = true;
        }
    }

    @Override
    public void save() {

    }
}
