import imagescaler.domain.ImageScalerService;
import imagescaler.infrastructure.scaler.IOImageScalerEngineFactory;

class ImageScalerServiceFactory {
    ImageScalerService get() {
        return new ImageScalerService(new IOImageScalerEngineFactory().get());
    }
}
