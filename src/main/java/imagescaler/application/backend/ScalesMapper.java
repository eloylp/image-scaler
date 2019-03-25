package imagescaler.application.backend;

import imagescaler.domain.ImageScalerException;
import imagescaler.domain.Scale;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class ScalesMapper {

    private static final String SCALE_PATTER = "^[0-9]+((?:[0-9]+)x(?:[0-9]+),?)+[0-9]$";

    List<Scale> map(String rawScales) throws ImageScalerException {

        if (!rawScales.matches(SCALE_PATTER)) {
            throw new ImageScalerException("Cannot parse scales argument :" + rawScales);
        }

        String[] scales = rawScales.split(",");

        List<Scale> results = new ArrayList<>();

        for (String scale : scales) {
            String[] values = scale.split("x");
            Scale scaleResult = new Scale(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            results.add(scaleResult);
        }

        return results;
    }
}
