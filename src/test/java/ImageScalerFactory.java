import imagescaler.domain.ImageScalerImpl;
import imagescaler.infrastructure.scaler.IOImageScalerEngineFactory;

class ImageScalerFactory {
    ImageScalerImpl get() {
        return new ImageScalerImpl(new IOImageScalerEngineFactory().get());
    }
}
