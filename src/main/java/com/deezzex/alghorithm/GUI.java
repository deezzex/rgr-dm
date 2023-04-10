package com.deezzex.alghorithm;

import com.deezzex.util.MatrixUtil;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.Arrays;

public class GUI extends JFrame {

    public GUI() {
        super("Travelling Salesman Problem solver by Christofides algorithm.");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JLabel inputLabel = new JLabel("   Вхідна матриця: ");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextArea inputTextArea = new JTextArea(15, 35);
        inputTextArea.setEditable(false);
        inputTextArea.setLineWrap(true);
        inputTextArea.setFont(new Font("Arial", Font.PLAIN, 8));
        inputTextArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);

        JLabel resultsLabel = new JLabel("   Шлях комівояжера: ");
        resultsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextArea resultsTextArea = new JTextArea(3, 40);
        resultsTextArea.setEditable(false);
        resultsTextArea.setLineWrap(true);
        resultsTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        resultsTextArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane resultsScrollPane = new JScrollPane(resultsTextArea);

        JLabel conclusionLabel = new JLabel("   Довжина шляху: ");
        conclusionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextArea conclusionTextArea = new JTextArea(3, 40);
        conclusionTextArea.setEditable(false);
        conclusionTextArea.setLineWrap(true);
        conclusionTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        conclusionTextArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane conclusionScrollPane = new JScrollPane(conclusionTextArea);

        JButton openFileButton = new JButton("Відкрити файл");
        openFileButton.setPreferredSize(new Dimension(openFileButton.getWidth(), 80));
        openFileButton.setBackground(Color.RED);
        openFileButton.setFocusPainted(true);

        openFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(GUI.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    SalesmanProblemSolver solver = new SalesmanProblemSolver();
                    double[][] inputMatrix = MatrixUtil.readMatrix(file.getAbsolutePath());
                    int[] path = solver.solveByChristofides(inputMatrix);

                    inputTextArea.setText("");
                    for (double[] curRow : inputMatrix) {
                        for (double curCol : curRow){
                            inputTextArea.append((int) curCol + " ");
                        }
                        inputTextArea.append("\n");

                    }

                    resultsTextArea.setText("");
                    resultsTextArea.append(Arrays.toString(path));

                    Integer lengthOfRoute = solver.getLengthOfRoute();

                    conclusionTextArea.setText("");
                    conclusionTextArea.append(String.valueOf(lengthOfRoute));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JPanel textAreaPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        JPanel labelPanel = new JPanel(new GridLayout(3, 1, 20, 20));

        textAreaPanel.add(inputScrollPane);
        textAreaPanel.add(resultsScrollPane);
        textAreaPanel.add(conclusionScrollPane);
        labelPanel.add(inputLabel);
        labelPanel.add(resultsLabel);
        labelPanel.add(conclusionLabel);

        setLayout(new BorderLayout(20, 20));
        add(textAreaPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.WEST);
        add(openFileButton, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
