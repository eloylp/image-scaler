package imagescaler.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class NameTest {

    private final String name;
    private final boolean mustPass;

    @Parameters
    public static Collection<Object[]> samples() {
        return Arrays.asList(new Object[][]{
                {"image.png", true},
                {"image.jpg", true},
                {"jg", false},
        });
    }

    public NameTest(String name, boolean mustPass) {
        this.name = name;
        this.mustPass = mustPass;
    }

    @Test
    public void testAcceptedNames() throws ImageScalerException {
        if (mustPass) {
            Name resultName = new Name(name);
            assertEquals(name, resultName.toString());
        } else {
            Assertions.assertThrows(ImageScalerException.class, () -> new Name(name));
        }
    }

}