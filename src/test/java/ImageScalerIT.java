import imagescaler.domain.*;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ImageScalerIT {

    private final TestSample testSample;

    @Parameters
    public static TestSample[] samples() {
        return new TestSample[]{
                new TestSample("tux.jpg", "image/jpg", 57590),
                new TestSample("tux.png", "image/png", 11910),
                new TestSample("tux.jpeg", "image/jpeg", 57590),
        };
    }

    static class TestSample {
        private final String file;
        private final String contentType;
        private final int size;

        TestSample(String file, String contentType, int size) {
            this.file = file;
            this.contentType = contentType;
            this.size = size;
        }
    }

    public ImageScalerIT(TestSample testSample) {
        this.testSample = testSample;
    }

    @Test
    public void testScaling() throws IOException, ImageScalerException {
        ImageScalerImpl imageScaler = new ImageScalerFactory().get();

        byte[] imageData = IOUtils.toByteArray(this.getClass().getResource(this.testSample.file));

        Image image = new ImageBuilder(
                this.testSample.file,
                new ImageInfoBuilder(
                        this.testSample.contentType,
                        new ScaleBuilder(265, 314).build(), this.testSample.size
                ).build(), imageData
        ).build();

        Scale scaleA = new ScaleBuilder(50, 50).build();
        Scale scaleB = new ScaleBuilder(30, 30).build();

        List<Scale> scales = new ArrayList<>();
        scales.add(scaleA);
        scales.add(scaleB);

        List<Image> result = imageScaler.scaleImageTo(image, scales);

        for (Image resultantImage : result) {
            assertTrue("Image copies must have at least some bytes.", resultantImage.getData().length > 1000);
            assertEquals(this.testSample.contentType, resultantImage.getContentType());
        }

    }
}
