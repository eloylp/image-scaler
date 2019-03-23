package imagescaler.domain;

import java.util.List;

public interface ImageRepository {
    void save(Image image);

    List<Image> findAll() throws ImageScalerException;
}
