package imagescaler.domain;

public class Image {

    private String name;
    private Byte[] data;

    public Image(String name, Byte[] data) {

        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public Byte[] getData() {
        return data;
    }
}
