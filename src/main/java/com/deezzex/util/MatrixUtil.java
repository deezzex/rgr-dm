package com.deezzex.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatrixUtil {

    public static double[][] readMatrix(final String pathToFile) throws IOException {
        double[][] matrix;
        BufferedReader br = new BufferedReader(new FileReader(pathToFile));

        StringBuilder build = new StringBuilder();

        int numCities = 0;
        while (!build.append(br.readLine()).toString().equalsIgnoreCase("null")) {
            numCities++;
            build.setLength(0);
        }
        matrix = new double[numCities][numCities];

        br = new BufferedReader(new FileReader(pathToFile));

        int currentCity = 0;
        build = new StringBuilder();
        while (!build.append(br.readLine()).toString().equalsIgnoreCase("null")) {
            String[] tokens = build.toString().split(" ");
            for (int i = 0; i < numCities; i++) {
                matrix[currentCity][i] = Integer.parseInt(tokens[i]);
            }
            currentCity++;
            build.setLength(0);
        }
        return matrix;
    }
}