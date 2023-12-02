package com.example.superjjj.util;

public class Matrix {

    public static int[][] getTranspose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] result = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }

        return result;
    }

    /**
     * 加法
     * @param aMatrix
     * @param bMatrix
     * @return
     * @throws IllegalArgumentException
     */
    public static int[][] add(int[][] aMatrix, int[][] bMatrix) throws IllegalArgumentException {
        // 添加维度检查
        if (aMatrix.length != bMatrix.length || aMatrix[0].length != bMatrix[0].length) {
            throw new IllegalArgumentException("矩阵维度不匹配");
        }

        int rows = aMatrix.length;
        int cols = aMatrix[0].length;
        int[][] result = new int[rows][cols];

        // 执行加法操作
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = aMatrix[i][j] + bMatrix[i][j];
            }
        }

        return result;
    }

    /**
     * 减法
     * @param aMatrix
     * @param bMatrix
     * @return
     * @throws IllegalArgumentException
     */
    public static int[][] minus(int[][] aMatrix, int[][] bMatrix) throws IllegalArgumentException {
        // 添加维度检查
        if (aMatrix.length != bMatrix.length || aMatrix[0].length != bMatrix[0].length) {
            throw new IllegalArgumentException("矩阵维度不匹配");
        }

        int rows = aMatrix.length;
        int cols = aMatrix[0].length;
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = aMatrix[i][j] - bMatrix[i][j];
            }
        }

        return result;
    }

    /**
     * 乘法
     * @param aMatrix
     * @param bMatrix
     * @return
     * @throws IllegalArgumentException
     */
    public static int[][] multiply(int[][] aMatrix, int[][] bMatrix) throws IllegalArgumentException {
        int aRows = aMatrix.length;
        int aCols = aMatrix[0].length;

        int bRows = bMatrix.length;
        int bCols = bMatrix[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("矩阵无法相乘，维度不匹配");
        }

        int[][] result = new int[aRows][bCols];

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                for (int k = 0; k < aCols; k++) {
                    result[i][j] += aMatrix[i][k] * bMatrix[k][j];
                }
            }
        }

        return result;
    }

    /**
     * 行列式
     * @param matrix
     * @return
     */
    public static int determinant(int[][] matrix) {
        int result = 0;

        // 单一元素的矩阵
        if (matrix.length == 1) {
            result = matrix[0][0];
            return result;
        }

        // 2x2 矩阵
        if (matrix.length == 2) {
            result = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            return result;
        }

        // 更大的矩阵
        for (int i = 0; i < matrix[0].length; i++) {
            int[][] temp = new int[matrix.length - 1][matrix[0].length - 1];

            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    if (k < i) {
                        temp[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        temp[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            result += matrix[0][i] * Math.pow(-1, (int) i) * determinant(temp);
        }

        return result;
    }

    /**
     * 伴随矩阵
     * @param matrix
     * @return
     */
    public static int[][] adjugate(int[][] matrix) {
        int[][] adj = new int[matrix.length][matrix.length];

        if (matrix.length == 1) {
            adj[0][0] = 1;
            return adj;
        }

        int sign = 1;
        int[][] temp = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                // 获取余子矩阵
                getCofactor(matrix, temp, i, j, matrix.length);

                // 计算余子矩阵的行列式
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = (sign) * (determinant(temp));
            }
        }

        return adj;
    }


    /**
     * 代数余子式
     * @param matrix
     * @param temp
     * @param p
     * @param q
     * @param n
     */
    private static void getCofactor(int[][] matrix, int[][] temp, int p, int q, int n) {
        int i = 0, j = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = matrix[row][col];

                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    /**
     * 对偶矩阵
     * @param vector
     * @return
     */
    public static int[][] dual(int[] vector) {
        int[][] result = new int[3][3];

        result[0][0] = 0;
        result[0][1] = -vector[2];
        result[0][2] = vector[1];
        result[1][0] = vector[2];
        result[1][1] = 0;
        result[1][2] = -vector[0];
        result[2][0] = -vector[1];
        result[2][1] = vector[0];
        result[2][2] = 0;

        return result;
    }

    /**
     * 秩
     * @param matrix
     * @return
     */
    public static int rankOfMatrix(int[][] matrix) {
        int rank = matrix[0].length;
        int rows = matrix.length;

        double[][] tempMatrix = new double[rows][rank];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rank; j++) {
                tempMatrix[i][j] = matrix[i][j];
            }
        }

        for (int row = 0; row < rank; row++) {
            if (tempMatrix[row][row] != 0) {
                for (int col = 0; col < rows; col++) {
                    if (col != row) {
                        double mult = tempMatrix[col][row] / tempMatrix[row][row];
                        for (int i = 0; i < rank; i++)
                            tempMatrix[col][i] -= mult * tempMatrix[row][i];
                    }
                }
            } else {
                boolean reduce = true;

                for (int i = row + 1; i < rows; i++) {
                    if (tempMatrix[i][row] != 0) {
                        swap(tempMatrix, row, i, rank);
                        reduce = false;
                        break;
                    }
                }

                if (reduce) {
                    rank--;

                    for (int i = 0; i < rows; i++)
                        tempMatrix[i][row] = tempMatrix[i][rank];
                }

                row--;
            }
        }

        return rank;
    }

    private static void swap(double[][] matrix, int row1, int row2, int col) {
        for (int i = 0; i < col; i++) {
            double temp = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = temp;
        }
    }


}
