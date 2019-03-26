import imagescaler.domain.*;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImageScalerIntegration {

    @Test
    public void testScaling() throws IOException, ImageScalerException {
        ImageScalerImpl imageScaler = new ImageScalerFactory().get();

        byte[] imageData = IOUtils.toByteArray(this.getClass().getResource("tux.png"));

        Image image = new ImageBuilder(
                "tux.png",
                new ImageInfoBuilder("image/png", new ScaleBuilder(265, 314).build(),11910).build(),
                imageData).build();

        Scale scaleA = new ScaleBuilder(50, 50).build();
        Scale scaleB = new ScaleBuilder(30, 30).build();

        List<Scale> scales = new ArrayList<>();
        scales.add(scaleA);
        scales.add(scaleB);

        List<Image> result = imageScaler.scaleImageTo(image, scales);

    }
}
