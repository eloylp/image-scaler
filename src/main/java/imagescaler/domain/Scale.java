package imagescaler.domain;

public class Scale {
    private final int width;
    private final int height;

    public Scale(int width, int height) throws ImageScalerException {
        this.width = checkValue(width);
        this.height = checkValue(height);
    }

    private int checkValue(int measure) throws ImageScalerException {
        if (measure < 1) {
            throw new ImageScalerException("Scales must be at least 1 pixel");
        }
        return measure;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
