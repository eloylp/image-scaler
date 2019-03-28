package imagescaler.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ImageScalerImplTest  {

    private ImageScalerImpl sut;
    private ImageScalerEngine imageScalerEngine;

    @BeforeEach
    void setUp() {
        imageScalerEngine = mock(ImageScalerEngine.class);
        sut = new ImageScalerImpl(imageScalerEngine);
    }

    @Test
    void scaleImageTo() throws ImageScalerException {

        Image originalImage = mock(Image.class);
        Uuid expectedGroup = new Uuid("9d4ba642-e20c-4ec5-8394-86f98c6b213c");
        when(originalImage.getGroupUuid()).thenReturn(expectedGroup);
        Scale scale1 = mock(Scale.class);
        Scale scale2 = mock(Scale.class);

        Image resultImage1 = mock(Image.class);
        Image resultImage2 = mock(Image.class);

        doReturn(resultImage1).when(imageScalerEngine).scaleImageTo(originalImage, scale1);
        doReturn(resultImage2).when(imageScalerEngine).scaleImageTo(originalImage, scale2);


        List<Scale> targetScales = new ArrayList<>();
        targetScales.add(scale1);
        targetScales.add(scale2);

        List<Image> result = sut.scaleImageTo(originalImage, targetScales);

        assertEquals(2, result.size());
        verify(resultImage1).setGroupUuid(originalImage.getGroupUuid());
        verify(resultImage2).setGroupUuid(originalImage.getGroupUuid());
    }
}