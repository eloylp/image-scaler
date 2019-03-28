package imagescaler.application.scaler;

import imagescaler.domain.ImageScalerException;
import imagescaler.domain.Scale;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ScalesMapperTest {

    private final String scaleSample;
    private final boolean mustPass;

    @Parameters
    public static Collection<Object[]> samples() {
        return Arrays.asList(new Object[][]{
                {"100x100,300x300, 600x 300", false},
                {"100x100,300X300,600X300", false},
                {"100x100,300x300,600x300", true},
        });
    }

    public ScalesMapperTest(String scaleSample, boolean mustPass) {
        this.scaleSample = scaleSample;
        this.mustPass = mustPass;
    }

    @Test
    public void map() throws ImageScalerException {
        ScalesMapper scalesMapper = new ScalesMapperBuilder().build();

        if (mustPass) {

            List<Scale> result = scalesMapper.map(this.scaleSample);

            assertEquals(result.get(0).getWidth(), 100);
            assertEquals(result.get(0).getHeight(), 100);

            assertEquals(result.get(1).getWidth(), 300);
            assertEquals(result.get(1).getHeight(), 300);

            assertEquals(result.get(2).getWidth(), 600);
            assertEquals(result.get(2).getHeight(), 300);

        } else {
            Assertions.assertThrows(ImageScalerException.class, () -> scalesMapper.map(this.scaleSample));
        }
    }
}