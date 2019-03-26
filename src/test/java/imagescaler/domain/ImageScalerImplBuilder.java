package imagescaler.domain;

public class ImageScalerImplBuilder {

    private ImageScalerEngine imageScalerEngine;

    public ImageScalerImplBuilder(ImageScalerEngine imageScalerEngine) {
        this.imageScalerEngine = imageScalerEngine;
    }

    public ImageScalerImpl build() {
        return new ImageScalerImpl(imageScalerEngine);
    }
}