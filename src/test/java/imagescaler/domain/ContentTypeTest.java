package imagescaler.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class ContentTypeTest {

    private final String contentType;
    private final boolean mustPass;

    @Parameters
    public static Collection<Object[]> samples() {
        return Arrays.asList(new Object[][]{
                {"image/png", true},
                {"image/jpg", true},
                {"image/jpeg", true},
                {"image/tiff", false},
                {"application/json", false},
        });
    }

    public ContentTypeTest(String contentType, boolean mustPass) {
        this.contentType = contentType;
        this.mustPass = mustPass;
    }

    @Test
    public void testAcceptedContentTypes() throws ImageScalerException {
        if (mustPass) {
            new ContentType(contentType);
        } else {
            Assertions.assertThrows(ImageScalerException.class, () -> new ContentType(contentType));
        }
    }


}