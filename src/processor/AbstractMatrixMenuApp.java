package processor;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public abstract class AbstractMatrixMenuApp {
    protected final Scanner scanner;

    public AbstractMatrixMenuApp(Scanner scanner) {
        this.scanner = scanner;
    }

    protected <T extends MenuOption> T readMenuOption(T[] menuOptions) {
        System.out.println();
        for (T menuOption : menuOptions) {
            System.out.println(menuOption.getView());
        }
        while(true) {
            System.out.print("Your choice: ");
            final int code = Integer.parseInt(scanner.nextLine());
            for (T menuOption : menuOptions) {
                if (menuOption.getCode() == code) {
                    return menuOption;
                }
            }
        }
    }

    protected Matrix readMatrix() {
        return readMatrix("");
    }

    protected Matrix readMatrix(String matrixName) {
        System.out.print(matrixName.isEmpty() ? "Enter matrix size: " :
                String.format("Enter size of %s matrix: ", matrixName));
        int[] dimensions = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        int rows = dimensions[0];
        int columns = dimensions[1];
        System.out.println(matrixName.isEmpty() ? "Enter matrix:" :
                String.format("Enter %s matrix:", matrixName));
        return new Matrix(
                Stream.generate(scanner::nextLine)
                        .map(line -> Arrays.stream(line.split("\\s+", columns))
                                .mapToDouble(Double::parseDouble)
                                .toArray())
                        .limit(rows)
                        .toArray(double[][]::new)
        );
    }
}
