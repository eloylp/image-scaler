package imagescaler.domain;

public class ScaleBuilder {

    private final int width;
    private final int height;

    public ScaleBuilder(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Scale build() {
        return new Scale(width, height);
    }

}
