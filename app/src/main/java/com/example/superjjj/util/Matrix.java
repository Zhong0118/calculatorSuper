package com.example.superjjj.util;

public class Matrix {

    public static double[][] getTranspose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] result = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }

        return result;
    }

    /**
     * 加法
     *
     * @param aMatrix
     * @param bMatrix
     * @return
     * @throws IllegalArgumentException
     */
    public static double[][] add(double[][] aMatrix, double[][] bMatrix) throws IllegalArgumentException {
        // 添加维度检查
        if (aMatrix.length != bMatrix.length || aMatrix[0].length != bMatrix[0].length) {
            throw new IllegalArgumentException("矩阵维度不匹配");
        }

        int rows = aMatrix.length;
        int cols = aMatrix[0].length;
        double[][] result = new double[rows][cols];

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
     *
     * @param aMatrix
     * @param bMatrix
     * @return
     * @throws IllegalArgumentException
     */
    public static double[][] minus(double[][] aMatrix, double[][] bMatrix) throws IllegalArgumentException {
        // 添加维度检查
        if (aMatrix.length != bMatrix.length || aMatrix[0].length != bMatrix[0].length) {
            throw new IllegalArgumentException("矩阵维度不匹配");
        }

        int rows = aMatrix.length;
        int cols = aMatrix[0].length;
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = aMatrix[i][j] - bMatrix[i][j];
            }
        }

        return result;
    }

    /**
     * 乘法
     *
     * @param aMatrix
     * @param bMatrix
     * @return
     * @throws IllegalArgumentException
     */
    public static double[][] multiply(double[][] aMatrix, double[][] bMatrix) throws IllegalArgumentException {
        int aRows = aMatrix.length;
        int aCols = aMatrix[0].length;

        int bRows = bMatrix.length;
        int bCols = bMatrix[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("矩阵无法相乘，维度不匹配");
        }

        double[][] result = new double[aRows][bCols];

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
     *
     * @param matrix
     * @return
     */
    public static double determinant(double[][] matrix) {
        int n = matrix.length;
        double[][] lu = new double[n][n];
        int swaps = 0;

        // 将输入矩阵复制到lu矩阵中
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                lu[i][j] = matrix[i][j];
            }
        }

        // 进行LU分解
        for (int k = 0; k < n; k++) {
            // 部分主元选取
            int pivot = k;
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(lu[i][k]) > Math.abs(lu[pivot][k])) {
                    pivot = i;
                }
            }

            // 交换行
            if (pivot != k) {
                double[] temp = lu[pivot];
                lu[pivot] = lu[k];
                lu[k] = temp;
                swaps++;
            }

            // 检查奇异矩阵
            if (Math.abs(lu[k][k]) < 1e-9) {
                return 0;
            }

            // 分解步骤
            for (int i = k + 1; i < n; i++) {
                lu[i][k] /= lu[k][k];
                for (int j = k + 1; j < n; j++) {
                    lu[i][j] -= lu[i][k] * lu[k][j];
                }
            }
        }

        // 计算行列式
        double det = (swaps % 2 == 0) ? 1 : -1;
        for (int i = 0; i < n; i++) {
            det *= lu[i][i];
        }

        return det;
    }

    /**
     * 伴随矩阵
     *
     * @param matrix
     * @return
     */
    public static double[][] adjugate(double[][] matrix) {
        int n = matrix.length;
        double[][] adj = new double[n][n];
        double[][] cofactorMatrix = new double[n - 1][n - 1];

        if (n == 1) {
            adj[0][0] = 1;
            return adj;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                getCofactor(matrix, cofactorMatrix, i, j, n);
                int sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[i][j] = sign * determinant(cofactorMatrix);
            }
        }

        return getTranspose(adj); // 使用已有的转置方法
    }


    /**
     * 代数余子式
     *
     * @param matrix
     * @param temp
     * @param p
     * @param q
     * @param n
     */
    private static void getCofactor(double[][] matrix, double[][] temp, int p, int q, int n) {
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
     *
     * @param vector
     * @return
     */
    public static double[][] dual(double[] vector) {
        double[][] result = new double[3][3];

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
     *
     * @param matrix
     * @return
     */
    public static int rankOfMatrix(double[][] matrix) {
        int m = matrix.length; // number of rows
        int n = matrix[0].length; // number of columns
        int rank = 0;
        boolean[] rowSelected = new boolean[m];

        for (int col = 0; col < n; col++) {
            int i;
            for (i = 0; i < m; i++) {
                if (!rowSelected[i] && Math.abs(matrix[i][col]) > 1E-10) {
                    break;
                }
            }

            if (i < m) {
                rank++;
                rowSelected[i] = true;
                for (int k = 0; k < m; k++) {
                    if (k != i) {
                        double factor = matrix[k][col] / matrix[i][col];
                        for (int j = col; j < n; j++) {
                            matrix[k][j] -= factor * matrix[i][j];
                        }
                    }
                }
            }
        }
        return rank;
    }

    public static double[][] inverse(double[][] matrix) throws IllegalArgumentException {
        double det = determinant(matrix);
        if (Math.abs(det) < 1e-9) {
            double[][] a = {{0}};
            return a;
        }

        double[][] adj = adjugate(matrix);
        return multiplyByConstant(adj, 1.0 / det);
    }

    /**
     * 矩阵乘以常数
     *
     * @param matrix
     * @param constant
     * @return
     */
    private static double[][] multiplyByConstant(double[][] matrix, double constant) {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i][j] = matrix[i][j] * constant;
            }
        }
        return result;
    }


}
