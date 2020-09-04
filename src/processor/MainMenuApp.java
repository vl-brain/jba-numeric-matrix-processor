package processor;

import java.util.Scanner;

public class MainMenuApp extends AbstractMatrixMenuApp {

    private TransposeMatrixMenuApp transposeMatrixMenuApp;

    public MainMenuApp(Scanner scanner) {
        super(scanner);
        transposeMatrixMenuApp = new TransposeMatrixMenuApp(scanner);
    }

    public void run() {
        while (true) {
            final MainMenuOption option = readMenuOption(MainMenuOption.values());
            switch (option) {
                case ADD_MATRICES:
                    addMatrices();
                    break;
                case MULTIPLY_MATRIX_TO_CONST:
                    multiplyMatrix();
                    break;
                case MULTIPLY_MATRICES:
                    multiplyMatrices();
                    break;
                case TRANSPOSE_MATRIX:
                    transposeMatrixMenuApp.run();
                    break;
                case MATRIX_DETERMINANT:
                    calculateDeterminant();
                    break;
                case INVERSE_MATRIX:
                    inverseMatrix();
                    break;
                case EXIT:
                    return;
            }
        }
    }

    private void addMatrices() {
        final Matrix matrix1 = readMatrix("first");
        final Matrix matrix2 = readMatrix("second");
        if (matrix1.add(matrix2)) {
            System.out.println("The addition result is:");
            System.out.println(matrix1);
        } else {
            System.out.println("ERROR");
        }
    }

    private void multiplyMatrix() {
        final Matrix matrix = readMatrix();
        System.out.print("Enter constant:");
        final double constant = Double.parseDouble(scanner.nextLine());
        matrix.multiply(constant);
        System.out.println("The multiplication result is:");
        System.out.println(matrix);
    }

    private void multiplyMatrices() {
        final Matrix matrix1 = readMatrix("first");
        final Matrix matrix2 = readMatrix("second");
        if (matrix1.multiply(matrix2)) {
            System.out.println("The multiplication result is:");
            System.out.println(matrix1);
        } else {
            System.out.println("ERROR");
        }
    }

    private void calculateDeterminant() {
        final Matrix matrix = readMatrix();
        if (matrix.hasDeterminant()) {
            System.out.println("The result is:");
            System.out.println(matrix.determinant());
        } else {
            System.out.println("ERROR");
        }
    }

    private void inverseMatrix() {
        final Matrix matrix = readMatrix();
        if (matrix.inverseMatrix()) {
            System.out.println("The result is:");
            System.out.println(matrix);
        } else {
            System.out.println("ERROR");
        }
    }
}
