package processor;

public enum MainMenuOption implements MenuOption {
    ADD_MATRICES("Add matrices", 1),
    MULTIPLY_MATRIX_TO_CONST("Multiply matrix to a constant", 2),
    MULTIPLY_MATRICES("Multiply matrices", 3),
    TRANSPOSE_MATRIX("Transpose matrix", 4),
    MATRIX_DETERMINANT("Calculate a determinant", 5),
    INVERSE_MATRIX("Inverse matrix", 6),
    EXIT("Exit", 0);
    private final String description;
    private final int code;

    MainMenuOption(String description, int code) {
        this.description = description;
        this.code = code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCode() {
        return code;
    }

}
