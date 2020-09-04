package processor;

import java.util.Scanner;

public class TransposeMatrixMenuApp extends AbstractMatrixMenuApp {
    public TransposeMatrixMenuApp(Scanner scanner) {
        super(scanner);
    }

    public void run() {
        final MatrixTransposeMenuOption option = readMenuOption(MatrixTransposeMenuOption.values());
        final Matrix matrix = readMatrix();
        switch (option) {
            case MAIN_DIAGONAL:
                matrix.transpose();
                break;
            case SIDE_DIAGONAL:
                matrix.sideTranspose();
                break;
            case VERTICAL_LINE:
                matrix.mirrowVertical();
                break;
            case HORIZONTAL_LINE:
                matrix.mirrowHorizontal();
                break;
        }
        System.out.println("The result is:");
        System.out.println(matrix);
    }

}
