package model;

import javax.persistence.*;

@Entity
@Table(name = "product_file")
public class ProductFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_file_id", unique = true)
    private int id;

    @Lob
    @Column(name = "file", columnDefinition = "longblob")
    private byte[] file;

    public void setFile(byte[] file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public byte[] getFile() {
        return file;
    }
}
