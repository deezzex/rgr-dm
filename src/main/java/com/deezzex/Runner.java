package com.deezzex;

import com.deezzex.alghorithm.SalesmanProblemSolver;
import com.deezzex.util.MatrixUtil;

import java.io.IOException;
import java.util.Arrays;

class Runner {

    public static void main(String[] args) throws IOException {

        SalesmanProblemSolver solver = new SalesmanProblemSolver();
        double[][] inputMatrix = MatrixUtil.readMatrix("C:\\Users\\pshti\\IdeaProjects\\rgr-dm\\src\\main\\resources\\data\\input1.txt");

        int[] finalRoute = solver.solveByChristofides(inputMatrix);
        Integer lengthOfRoute = solver.getLengthOfRoute();

        System.out.println(Arrays.toString(finalRoute));
        System.out.println(lengthOfRoute);
    }
}
