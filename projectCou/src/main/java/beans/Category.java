package beans;

public enum Category {
    FASHION,
    ELECTRICITY,
    RESTAURANT,
    VACATION,
    ENTERTAINMENT;

    public final int value = 1 + ordinal();
}
