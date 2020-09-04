package processor;

public enum MatrixTransposeMenuOption implements MenuOption {
    MAIN_DIAGONAL(1, "Main diagonal"),
    SIDE_DIAGONAL(2, "Side diagonal"),
    VERTICAL_LINE(3, "Vertical line"),
    HORIZONTAL_LINE(4, "Horizontal line"),
    ;
    private final int code;
    private final String description;

    MatrixTransposeMenuOption(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
