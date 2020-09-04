package processor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Matrix {
    private double[][] items;

    public Matrix(double[][] items) {
        if (items == null || items.length == 0 || items[0].length == 0) {
            throw new IllegalArgumentException("Items required!");
        }
        this.items = items;
    }

    public Matrix(Matrix other) {
        this(Arrays.stream(other.items)
                .map(double[]::clone)
                .toArray(double[][]::new));
    }

    private int getRowsCount() {
        return items.length;
    }

    private int getColumnCount() {
        return items[0].length;
    }

    @Override
    public String toString() {
        return Arrays.stream(items)
                .map(row -> Arrays.stream(row)
                        .mapToObj(Double::toString)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }

    public boolean add(Matrix other) {
        if (getRowsCount() != other.getRowsCount() || getColumnCount() != other.getColumnCount()) return false;
        for (int i = getRowsCount(); --i >= 0; ) {
            for (int j = getColumnCount(); --j >= 0; ) {
                items[i][j] += other.items[i][j];
            }
        }
        return true;
    }

    public boolean multiply(Matrix other) {
        if (getColumnCount() != other.getRowsCount()) return false;
        final Matrix source = new Matrix(this);
        items = new double[source.getRowsCount()][other.getColumnCount()];
        for (int i = source.getRowsCount(); --i >= 0; ) {
            for (int k = other.getColumnCount(); --k >= 0; ) {
                for (int j = other.getRowsCount(); --j >= 0; ) {
                    items[i][k] += source.items[i][j] * other.items[j][k];
                }
            }
        }
        return true;
    }

    public boolean multiply(double value) {
        for (int i = getRowsCount(); --i >= 0; ) {
            for (int j = getColumnCount(); --j >= 0; ) {
                items[i][j] *= value;
            }
        }
        return true;
    }

    public boolean transpose() {
        final Matrix source = new Matrix(this);
        items = new double[source.getColumnCount()][source.getRowsCount()];
        for (int i = source.getRowsCount(); --i >= 0; ) {
            for (int j = source.getColumnCount(); --j >= 0; ) {
                items[j][i] = source.items[i][j];
            }
        }
        return true;
    }

    public boolean sideTranspose() {
        final Matrix source = new Matrix(this);
        items = new double[source.getColumnCount()][source.getRowsCount()];
        final int sourceRowsCount = source.getRowsCount();
        final int sourceColumnsCount = source.getColumnCount();
        for (int i = sourceRowsCount; --i >= 0; ) {
            for (int j = sourceColumnsCount; --j >= 0; ) {
                items[j][i] = source.items[sourceRowsCount - 1 - i][sourceColumnsCount - 1 - j];
            }
        }
        return true;
    }

    public boolean mirrowVertical() {
        final int columnsCount = getColumnCount();
        for (int i = getRowsCount(); --i >= 0; ) {
            for (int j = columnsCount / 2; --j >= 0; ) {
                double temp = items[i][j];
                int k = columnsCount - 1 - j;
                items[i][j] = items[i][k];
                items[i][k] = temp;
            }
        }
        return true;
    }

    public boolean mirrowHorizontal() {
        final int rowsCount = getRowsCount();
        for (int j = getColumnCount(); --j >= 0; ) {
            for (int i = rowsCount / 2; --i >= 0; ) {
                double temp = items[i][j];
                int k = rowsCount - 1 - i;
                items[i][j] = items[k][j];
                items[k][j] = temp;
            }
        }
        return true;
    }

    public boolean hasDeterminant() {
        return getRowsCount() == getColumnCount();
    }

    public double determinant() {
        if (!hasDeterminant()) {
            throw new IllegalStateException("Can't calculate determinant!");
        }
        return calculateDeterminant(this);
    }

    private static double calculateDeterminant(Matrix matrix) {
        final int rowsCount = matrix.getRowsCount();
        if (rowsCount == 1) {
            return matrix.items[0][0];
        }
        if (rowsCount == 2) {
            return matrix.items[0][0] * matrix.items[1][1] - matrix.items[0][1] * matrix.items[1][0];
        }
        double sum = 0;
        int i = matrix.indexOfMaxZeroItemsRowOrDefault(0);
        for (int j = matrix.getColumnCount(); --j >= 0; ) {
            final double k = matrix.items[i][j];
            if (k == 0) {
                continue;
            }
            double value = calculateDeterminant(matrix.minor(i, j)) * k;
            sum += (i + j) % 2 == 0 ? value : -value;
        }
        return sum;
    }

    private int indexOfMaxZeroItemsRowOrDefault(int defaultValue) {
        int index = -1;
        int maxZeroItems = 0;
        for (int i = getRowsCount(); --i >= 0; ) {
            int zeroItems = 0;
            for (int j = getColumnCount(); --j >= 0; ) {
                if (items[i][j] == 0) {
                    zeroItems++;
                }
            }
            if (zeroItems > maxZeroItems) {
                index = i;
                maxZeroItems = zeroItems;
            }
        }
        return index < 0 ? defaultValue : index;
    }

    private Matrix minor(int row, int column) {
        final double[][] items = new double[getRowsCount() - 1][getColumnCount() - 1];
        for (int i = getRowsCount(), k = i - 1; --i >= 0; ) {
            if (i == row) continue;
            k--;
            for (int j = getColumnCount(), n = j - 1; --j >= 0; ) {
                if (j == column) continue;
                items[k][--n] = this.items[i][j];
            }
        }
        return new Matrix(items);
    }

    public boolean inverseMatrix() {
        if (!hasDeterminant()) {
            return false;
        }
        final double determinant = determinant();
        if (determinant == 0) {
            return false;
        }
        final Matrix source = new Matrix(this);
        final int sourceRowsCount = source.getRowsCount();
        final int sourceColumnsCount = source.getColumnCount();
        for (int i = sourceRowsCount; --i >= 0; ) {
            for (int j = sourceColumnsCount; --j >= 0; ) {
                final double value = calculateDeterminant(source.minor(i, j));
                items[i][j] = (i + j) % 2 == 0 ? value : -value;
            }
        }
        transpose();
        multiply(1 / determinant);
        return true;
    }
}
