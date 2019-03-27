package imagescaler.domain;

import java.util.UUID;

public class Uuid {

    private final UUID value;

    Uuid() {
        this(UUID.randomUUID().toString());
    }

    public Uuid(String value) {
        this.value = UUID.fromString(value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
