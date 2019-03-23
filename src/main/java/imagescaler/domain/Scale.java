package imagescaler.domain;

public class Scale {
    private int width;
    private int height;

    public Scale(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
