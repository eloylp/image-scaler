package imagescaler.domain;

public class ScaleBuilder {

    private int width;
    private int height;

    public ScaleBuilder(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Scale build() {
        return new Scale(width, height);
    }

}
