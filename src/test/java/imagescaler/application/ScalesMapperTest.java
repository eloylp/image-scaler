package imagescaler.application;

import imagescaler.domain.Scale;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ScalesMapperTest {

    @Test
    public void map() {
        String rawScales = "345x567, 120x80, 600x 300";
        ScalesMapper scalesMapper = new ScalesMapper();
        List<Scale> result = scalesMapper.map(rawScales);

        assertEquals(result.get(0).getWidth(), 345);
        assertEquals(result.get(0).getHeight(), 567);

        assertEquals(result.get(1).getWidth(), 120);
        assertEquals(result.get(1).getHeight(), 80);

        assertEquals(result.get(2).getWidth(), 600);
        assertEquals(result.get(2).getHeight(), 300);
    }
}