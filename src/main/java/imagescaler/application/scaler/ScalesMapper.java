package imagescaler.application.scaler;

import imagescaler.domain.ImageScalerException;
import imagescaler.domain.Scale;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class ScalesMapper {

    // This may allow the comma separated format like "100x100,300x300,600x300"

    private static final String SCALE_PATTER = "^[0-9]+((?:[0-9]+)x(?:[0-9]+),?)+[0-9]$";
    private static final String SCALE_SEPARATOR = ",";
    private static final String SCALE_VALUES_SEPARATOR = "x";

    List<Scale> map(String rawScales) throws ImageScalerException {

        if (!rawScales.matches(SCALE_PATTER)) {
            throw new ImageScalerException("Cannot parse scales argument :" + rawScales);
        }

        String[] scales = rawScales.split(SCALE_SEPARATOR);

        List<Scale> results = new ArrayList<>();

        for (String scale : scales) {
            String[] values = scale.split(SCALE_VALUES_SEPARATOR);
            Scale scaleResult = new Scale(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            results.add(scaleResult);
        }

        return results;
    }
}
