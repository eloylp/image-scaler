package imagescaler.application.backend;

import imagescaler.domain.Scale;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class ScalesMapper {

    List<Scale> map(String rawScales) {
        String[] scales = rawScales.split(",");
        List<Scale> results = new ArrayList<>();

        for (String scale : scales) {
            scale = scale.replace(" ", "");
            String[] values = scale.split("x");
            Scale scaleResult = new Scale(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            results.add(scaleResult);
        }

        return results;
    }
}