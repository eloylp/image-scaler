package imagescaler.infrastructure.scaler;

public class IOImageScalerEngineFactory {
    public IOImageScalerEngine get() {
        return new IOImageScalerEngine(new BufferedImageTypeCalculator(), new ImageFormatterCalculator());
    }
}