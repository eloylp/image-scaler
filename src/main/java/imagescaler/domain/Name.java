package imagescaler.domain;

public class Name {

    private final String value;
    private final int minLength = 3;

    public Name(String value) throws ImageScalerException {
        checkValue(value);
        this.value = value;
    }

    private void checkValue(String value) throws ImageScalerException {

        if (value.length() < minLength) {
            throw new ImageScalerException("The name must be at least " + minLength + " characters");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
