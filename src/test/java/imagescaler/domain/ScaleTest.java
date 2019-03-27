package imagescaler.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.*;


@RunWith(Parameterized.class)
public class ScaleTest {

    private final int width;
    private final int height;
    private final boolean mustPass;

    @Parameters
    public static Collection<Object[]> samples() {
        return Arrays.asList(new Object[][]{
                {100, 100, true},
                {200, 300, true},
                {0, 300, false},
                {300, 0, false},
                {-1, 0, false},
                {-1, 1, false},
                {1, -1, false},
                {-1, -1, false},
                {0, 0, false},
        });
    }

    public ScaleTest(int width, int height, boolean mustPass) {
        this.width = width;
        this.height = height;
        this.mustPass = mustPass;
    }

    @Test
    public void testAcceptedNames() throws ImageScalerException {
        if (mustPass) {
            new Scale(width, height);
        } else {
            Assertions.assertThrows(ImageScalerException.class, () -> new Scale(width, height));
        }
    }

}