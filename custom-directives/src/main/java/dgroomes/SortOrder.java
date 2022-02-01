package dgroomes;

public enum SortOrder {

    ASC("ascending"),
    DESC("descending");

    public final String description;

    SortOrder(String description) {
        this.description = description;
    }
}
