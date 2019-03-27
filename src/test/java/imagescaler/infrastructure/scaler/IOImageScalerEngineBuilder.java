package imagescaler.infrastructure.scaler;

public class IOImageScalerEngineBuilder {
    public IOImageScalerEngine build() {
        return new IOImageScalerEngine(new BufferedImageTypeCalculator(), new ImageFormatterCalculator());
    }
}