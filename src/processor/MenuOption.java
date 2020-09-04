package processor;

public interface MenuOption {
    int getCode();

    String getDescription();

    default String getView() {
        return String.format("%d. %s", getCode(), getDescription());
    }
}
