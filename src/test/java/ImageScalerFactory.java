import imagescaler.domain.ImageScalerImpl;
import imagescaler.domain.ImageScalerImplBuilder;
import imagescaler.infrastructure.scaler.IOImageScalerEngineBuilder;

class ImageScalerFactory {
    ImageScalerImpl get() {
        return new ImageScalerImplBuilder(new IOImageScalerEngineBuilder().build()).build();
    }
}
