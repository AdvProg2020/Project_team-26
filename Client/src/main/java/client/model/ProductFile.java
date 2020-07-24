package client.model;


public class ProductFile {

    private int id;

    private String name;

    private byte[] file;

    public ProductFile() {
    }

    public ProductFile(String name, byte[] file) {
        this.name = name;
        this.file = file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getFile() {
        return file;
    }
}
