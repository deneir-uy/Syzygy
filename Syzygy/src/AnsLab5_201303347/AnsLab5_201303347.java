package AnsLab5_201303347;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author deneir-uy
 */
class Sequence {

    String alignment;
    String sequence;
    int length;

    Sequence(String sequence) {
        this.alignment = "";
        this.sequence = sequence.trim();
        this.length = this.sequence.length() + 1;
    }
}

class Cell {

    Cell diagonal;
    Cell left;
    Cell top;
    int value;

    Cell(int value) {
        this.diagonal = null;
        this.left = null;
        this.top = null;
        this.value = value;
    }

    final int max(Cell diagonal, Cell left, Cell top, int score, int gap) {
        int diagonalSum = diagonal.value + score;
        int leftSum = left.value + gap;
        int topSum = top.value + gap;
        int max = diagonalSum;

        if (leftSum > max) {
            max = leftSum;
        }

        if (topSum > max) {
            max = topSum;
        }

        if (topSum == max) {
            this.top = top;
        }
        if (leftSum == max) {
            this.left = left;
        }
        if (diagonalSum == max) {
            this.diagonal = diagonal;
        }

        return max;
    }
}

public class AnsLab5_201303347 extends javax.swing.JFrame {

    static HashMap<String, HashMap<String, Integer>> pam;
    static HashMap<String, HashMap<String, Integer>> blosum;
    static HashMap<String, Integer> frequency1;
    static HashMap<String, Integer> frequency2;
    static ArrayList<String[]> alignments = new ArrayList<>();
    static Sequence sequence1;
    static Sequence sequence2;
    static Cell[][] matrix;

    public AnsLab5_201303347() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AnsLab5_201303347.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnsLab5_201303347.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnsLab5_201303347.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnsLab5_201303347.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        Scanner scan = new Scanner(System.in);

        sequence1 = new Sequence("GAATTCAGTTA");
        sequence2 = new Sequence("GGATCGA");
        frequency1 = initializeFrequencies(true);
        frequency2 = initializeFrequencies(true);
        matrix = initializeMatrix(sequence1.length, sequence2.length, true, -4);

        fillMatrixNucleotide(matrix, new int[]{5, -3, -4});
        printMatrix(matrix);
        globalBacktrack(matrix[sequence1.length - 1][sequence2.length - 1], 1, 1, "", "");
        printAlignment(sequence1, sequence2);
        countFrequencies(sequence1, sequence2);
        printFrequencies();

//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new AnsLab5_201303347().setVisible(true);
//            }
//        });
    }

    public static void fillMatrixNucleotide(Cell[][] matrix, int[] scoring) {
        Cell currentCell;
        int match = scoring[0];
        int mismatch = scoring[1];
        int gap = scoring[2];

        for (int i = 1; i < sequence2.length; i++) {
            for (int j = 1; j < sequence1.length; j++) {
                currentCell = matrix[j][i];

                if (sequence1.sequence.substring(j - 1, j).matches(
                        sequence2.sequence.substring(i - 1, i))) {
                    currentCell.value = currentCell.max(matrix[j - 1][i - 1],
                            matrix[j - 1][i], matrix[j][i - 1], match, gap);
                } else {
                    currentCell.value = currentCell.max(matrix[j - 1][i - 1],
                            matrix[j - 1][i], matrix[j][i - 1], mismatch, gap);
                }

                matrix[j][i] = currentCell;
            }
        }
    }

    public static void localFillMatrixProtein(Cell[][] matrix, int[] scoring) {

    }

    public static void globalFillMatrixProtein(Cell[][] matrix, int[] scoring) {
        Cell currentCell;

        for (int i = 1; i < sequence2.length; i++) {
            for (int j = 1; j < sequence1.length; j++) {
                currentCell = matrix[j][i];

                if (sequence1.sequence.substring(j - 1, j).matches(
                        sequence2.sequence.substring(i - 1, i))) {
                    currentCell.value = currentCell.max(matrix[j - 1][i - 1],
                            matrix[j - 1][i], matrix[j][i - 1], match, gap);
                } else {
                    currentCell.value = currentCell.max(matrix[j - 1][i - 1],
                            matrix[j - 1][i], matrix[j][i - 1], mismatch, gap);
                }

                matrix[j][i] = currentCell;
            }
        }
    }

    public static void globalBacktrack(Cell cell, int s1, int s2, String seq1, String seq2) {
        boolean test = false;

        if (cell.diagonal != null) {
            if (test == true) {
                seq1 = seq1.substring(2);
                seq2 = seq2.substring(2);
            }

            seq1 = " " + sequence1.sequence.substring(
                    sequence1.sequence.length() - s1, sequence1.sequence.
                    length() - s1 + 1) + seq1;
            seq2 = " " + sequence2.sequence.substring(
                    sequence2.sequence.length() - s2, sequence2.sequence.
                    length() - s2 + 1) + seq2;
            globalBacktrack(cell.diagonal, s1 + 1, s2 + 1, seq1, seq2);
            test = true;
        }

        if (cell.left != null) {
            if (test == true) {
                seq1 = seq1.substring(2);
                seq2 = seq2.substring(2);
            }

            seq1 = " " + sequence1.sequence.substring(
                    sequence1.sequence.length() - s1, sequence1.sequence.
                    length() - s1 + 1) + seq1;
            seq2 = " " + "_" + seq2;
            globalBacktrack(cell.left, s1 + 1, s2, seq1, seq2);
            test = true;
        }

        if (cell.top != null) {
            if (test == true) {
                seq1 = seq1.substring(2);
                seq2 = seq2.substring(2);
            }

            seq1 = " " + "_" + seq1;
            seq2 = " " + sequence2.sequence.substring(
                    sequence2.sequence.length() - s2, sequence2.sequence.
                    length() - s2 + 1) + seq2;
            globalBacktrack(cell.top, s1, s2 + 1, seq1, seq2);
            test = true;
        }

        if (cell.diagonal == null && cell.left == null && cell.top == null) {
            alignments.add(new String[]{seq1, seq2});
        } else {
            return;
        }
    }

    public static void countFrequencies(Sequence sequence1, Sequence sequence2) {
        String key;

        for (int i = 0; i < sequence1.sequence.length(); i++) {
            key = sequence1.sequence.substring(i, i + 1);

            frequency1.put(key, frequency1.get(key) + 1);
        }

        for (int i = 0; i < sequence2.sequence.length(); i++) {
            key = sequence2.sequence.substring(i, i + 1);

            frequency2.put(key, frequency2.get(key) + 1);
        }
    }

    public static void printFrequencies() {
        String format = "%-5s";
        String keys = "";
        String frequencies = "";
        Iterator f1 = frequency1.entrySet().iterator();
        Iterator f2 = frequency2.entrySet().iterator();

        while (f1.hasNext()) {
            Map.Entry pair = (Map.Entry) f1.next();
            keys = keys + pair.getKey();
            frequencies = frequencies + pair.getValue();
            f1.remove();
        }

        keys = keys.replaceAll(".(?=.)", String.format(format, "$0"));
        frequencies = frequencies.replaceAll(".(?=.)", String.format(format, "$0"));
        System.out.println(keys);
        System.out.println(frequencies);
        System.out.println("");
        keys = "";
        frequencies = "";

        while (f2.hasNext()) {
            Map.Entry pair = (Map.Entry) f2.next();
            keys = keys + pair.getKey();
            frequencies = frequencies + pair.getValue();
            f2.remove();
        }

        keys = keys.replaceAll(".(?=.)", String.format(format, "$0"));
        frequencies = frequencies.replaceAll(".(?=.)", String.format(format, "$0"));
        System.out.println(keys);
        System.out.println(frequencies);
    }

    public static void printMatrix(Cell[][] matrix) {
        String format = "%-5s";

        for (int i = 0; i < sequence2.length; i++) {
            for (int j = 0; j < sequence1.length; j++) {
                System.out.printf(format, matrix[j][i].value);
            }
            System.out.println("");
        }
    }

    public static void printAlignment(Sequence s1, Sequence s2) {
        String matches = " ";

        for (int i = 0; i < alignments.size(); i++) {
            for (int j = 1; j < alignments.get(i)[0].length(); j += 2) {
                if (alignments.get(i)[0].substring(j, j + 1).matches(alignments.get(i)[1].substring(j, j + 1))) {
                    matches += "| ";
                } else {
                    matches += "  ";
                }
            }

            System.out.println(alignments.get(i)[0]);
            System.out.println(matches);
            System.out.println(alignments.get(i)[1]);
            System.out.println("");
            matches = " ";
        }

    }

    public static Cell[][] initializeMatrix(int m, int n, boolean isGlobal, int gap) {
        Cell[][] matrix = new Cell[m][n];

        if (isGlobal) {
            for (int i = 0; i < n; i++) {
                matrix[0][i] = new Cell(i * gap);
            }

            for (int i = 0; i < m; i++) {
                matrix[i][0] = new Cell(i * gap);
            }

            for (int i = 1; i < n; i++) {
                for (int j = 1; j < m; j++) {
                    matrix[j][i] = new Cell(0);
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                matrix[0][i] = new Cell(0);
            }

            for (int i = 0; i < m; i++) {
                matrix[i][0] = new Cell(0);
            }

            for (int i = 1; i < n; i++) {
                for (int j = 1; j < m; j++) {
                    matrix[j][i] = new Cell(0);
                }
            }
        }

        return matrix;
    }

    public static HashMap<String, Integer> initializeFrequencies(boolean isNucleotide) {
        HashMap<String, Integer> map = new HashMap<>();

        if (isNucleotide) {
            map.put("A", 0);
            map.put("C", 0);
            map.put("G", 0);
            map.put("T", 0);
        } else {
            map.put("A", 0);
            map.put("C", 0);
            map.put("D", 0);
            map.put("E", 0);
            map.put("F", 0);
            map.put("G", 0);
            map.put("H", 0);
            map.put("I", 0);
            map.put("K", 0);
            map.put("L", 0);
            map.put("M", 0);
            map.put("N", 0);
            map.put("P", 0);
            map.put("Q", 0);
            map.put("R", 0);
            map.put("S", 0);
            map.put("T", 0);
            map.put("V", 0);
            map.put("W", 0);
            map.put("Y", 0);
        }

        return map;
    }

    public static HashMap<String, HashMap<String, Integer>> initializePam() {
        HashMap<String, Integer> A = new HashMap<String, Integer>() {
            {
                put("A", 3);
                put("R", -1);
                put("N", -1);
                put("D", 0);
                put("C", -3);
                put("Q", -1);
                put("E", 0);
                put("G", 1);
                put("H", -3);
                put("I", -1);
                put("L", -3);
                put("K", -2);
                put("M", -2);
                put("F", -4);
                put("P", 1);
                put("S", 1);
                put("T", 1);
                put("W", -7);
                put("Y", -4);
                put("V", 0);
                put("B", 0);
                put("Z", -1);
                put("X", -1);
                put("*", -8);
            }
        };

        HashMap<String, Integer> R = new HashMap<String, Integer>() {
            {
                put("A", -3);
                put("R", 6);
                put("N", -1);
                put("D", -3);
                put("C", -4);
                put("Q", 1);
                put("E", -3);
                put("G", -4);
                put("H", 1);
                put("I", -2);
                put("L", -4);
                put("K", 2);
                put("M", -1);
                put("F", -5);
                put("P", -1);
                put("S", -1);
                put("T", -2);
                put("W", 1);
                put("Y", -5);
                put("V", -3);
                put("B", -2);
                put("Z", -1);
                put("X", -2);
                put("*", -8);
            }
        };

        HashMap<String, Integer> N = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", -1);
                put("N", 4);
                put("D", 2);
                put("C", -5);
                put("Q", 0);
                put("E", 1);
                put("G", 0);
                put("H", 2);
                put("I", -2);
                put("L", -4);
                put("K", 1);
                put("M", -3);
                put("F", -4);
                put("P", -2);
                put("S", 1);
                put("T", 0);
                put("W", -4);
                put("Y", -2);
                put("V", -3);
                put("B", 3);
                put("Z", 0);
                put("X", -1);
                put("*", -8);
            }
        };

        HashMap<String, Integer> D = new HashMap<String, Integer>() {
            {
                put("A", 0);
                put("R", -3);
                put("N", 2);
                put("D", 5);
                put("C", -7);
                put("Q", 1);
                put("E", 3);
                put("G", 0);
                put("H", 0);
                put("I", -3);
                put("L", -5);
                put("K", -1);
                put("M", -4);
                put("F", -7);
                put("P", -3);
                put("S", 0);
                put("T", -1);
                put("W", -8);
                put("Y", -5);
                put("V", -3);
                put("B", 4);
                put("Z", 3);
                put("X", -2);
                put("*", -8);
            }
        };

        HashMap<String, Integer> C = new HashMap<String, Integer>() {
            {
                put("A", -3);
                put("R", -4);
                put("N", -5);
                put("D", -7);
                put("C", 9);
                put("Q", -7);
                put("E", -7);
                put("G", -4);
                put("H", -4);
                put("I", -5);
                put("L", -7);
                put("K", -7);
                put("M", -6);
                put("F", -6);
                put("P", -4);
                put("S", 0);
                put("T", -3);
                put("W", -8);
                put("Y", -1);
                put("V", -3);
                put("B", -6);
                put("Z", -7);
                put("X", -4);
                put("*", -8);
            }
        };

        HashMap<String, Integer> Q = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", 1);
                put("N", 0);
                put("D", 1);
                put("C", -7);
                put("Q", 6);
                put("E", 2);
                put("G", -3);
                put("H", 3);
                put("I", -3);
                put("L", -2);
                put("K", 0);
                put("M", -1);
                put("F", -6);
                put("P", 0);
                put("S", -2);
                put("T", -2);
                put("W", -6);
                put("Y", -5);
                put("V", -3);
                put("B", 0);
                put("Z", 4);
                put("X", -1);
                put("*", -8);
            }
        };

        HashMap<String, Integer> E = new HashMap<String, Integer>() {
            {
                put("A", 0);
                put("R", -3);
                put("N", 1);
                put("D", 3);
                put("C", -7);
                put("Q", 2);
                put("E", 5);
                put("G", -1);
                put("H", -1);
                put("I", -3);
                put("L", -4);
                put("K", -1);
                put("M", -3);
                put("F", -7);
                put("P", -2);
                put("S", -1);
                put("T", -2);
                put("W", -8);
                put("Y", -5);
                put("V", -3);
                put("B", 3);
                put("Z", 4);
                put("X", -1);
                put("*", -8);
            }
        };

        HashMap<String, Integer> G = new HashMap<String, Integer>() {
            {
                put("A", 1);
                put("R", -4);
                put("N", 0);
                put("D", 0);
                put("C", -4);
                put("Q", -3);
                put("E", -1);
                put("G", 5);
                put("H", -4);
                put("I", -4);
                put("L", -5);
                put("K", -3);
                put("M", -4);
                put("F", -5);
                put("P", -2);
                put("S", 1);
                put("T", -1);
                put("W", -8);
                put("Y", -6);
                put("V", -2);
                put("B", 0);
                put("Z", -2);
                put("X", -2);
                put("*", -8);
            }
        };

        HashMap<String, Integer> H = new HashMap<String, Integer>() {
            {
                put("A", -3);
                put("R", 1);
                put("N", 2);
                put("D", 0);
                put("C", -4);
                put("Q", 3);
                put("E", -1);
                put("G", -4);
                put("H", 7);
                put("I", -4);
                put("L", -3);
                put("K", -2);
                put("M", -4);
                put("F", -3);
                put("P", -1);
                put("S", -2);
                put("T", -3);
                put("W", -3);
                put("Y", -1);
                put("V", -3);
                put("B", 1);
                put("Z", 1);
                put("X", -2);
                put("*", -8);
            }
        };

        HashMap<String, Integer> I = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", -2);
                put("N", -2);
                put("D", -3);
                put("C", -3);
                put("Q", -3);
                put("E", -3);
                put("G", -4);
                put("H", -4);
                put("I", 6);
                put("L", 1);
                put("K", -3);
                put("M", 1);
                put("F", 0);
                put("P", -3);
                put("S", -2);
                put("T", 0);
                put("W", -6);
                put("Y", -2);
                put("V", 3);
                put("B", -3);
                put("Z", -3);
                put("X", -1);
                put("*", -8);
            }
        };

        HashMap<String, Integer> L = new HashMap<String, Integer>() {
            {
                put("A", -3);
                put("R", -4);
                put("N", -4);
                put("D", -5);
                put("C", -7);
                put("Q", -2);
                put("E", -4);
                put("G", -5);
                put("H", -3);
                put("I", 1);
                put("L", 5);
                put("K", -4);
                put("M", 3);
                put("F", 0);
                put("P", -3);
                put("S", -4);
                put("T", -3);
                put("W", -3);
                put("Y", -2);
                put("V", 1);
                put("B", -4);
                put("Z", -3);
                put("X", -2);
                put("*", -8);
            }
        };

        HashMap<String, Integer> K = new HashMap<String, Integer>() {
            {
                put("A", -2);
                put("R", 2);
                put("N", 1);
                put("D", -1);
                put("C", -7);
                put("Q", 0);
                put("E", -1);
                put("G", -3);
                put("H", -2);
                put("I", -3);
                put("L", 4);
                put("K", 5);
                put("M", 0);
                put("F", -7);
                put("P", -2);
                put("S", -1);
                put("T", -1);
                put("W", -5);
                put("Y", -5);
                put("V", -4);
                put("B", 0);
                put("Z", -1);
                put("X", -2);
                put("*", -8);
            }
        };

        HashMap<String, Integer> M = new HashMap<String, Integer>() {
            {
                put("A", -2);
                put("R", -1);
                put("N", -3);
                put("D", -4);
                put("C", -6);
                put("Q", -1);
                put("E", -3);
                put("G", -4);
                put("H", -4);
                put("I", 1);
                put("L", 3);
                put("K", 0);
                put("M", 8);
                put("F", -1);
                put("P", -3);
                put("S", -2);
                put("T", -1);
                put("W", -6);
                put("Y", -4);
                put("V", 1);
                put("B", -4);
                put("Z", -2);
                put("X", -2);
                put("*", -8);
            }
        };

        HashMap<String, Integer> F = new HashMap<String, Integer>() {
            {
                put("A", -4);
                put("R", -5);
                put("N", -4);
                put("D", -7);
                put("C", -6);
                put("Q", -6);
                put("E", -7);
                put("G", -5);
                put("H", -3);
                put("I", 0);
                put("L", 0);
                put("K", -7);
                put("M", -1);
                put("F", 8);
                put("P", -5);
                put("S", -3);
                put("T", -4);
                put("W", -1);
                put("Y", 4);
                put("V", -3);
                put("B", -5);
                put("Z", -6);
                put("X", -3);
                put("*", -8);
            }
        };

        HashMap<String, Integer> P = new HashMap<String, Integer>() {
            {
                put("A", 1);
                put("R", -1);
                put("N", -2);
                put("D", -3);
                put("C", -4);
                put("Q", 0);
                put("E", -2);
                put("G", -2);
                put("H", -1);
                put("I", -3);
                put("L", -3);
                put("K", -2);
                put("M", -3);
                put("F", -5);
                put("P", 6);
                put("S", 1);
                put("T", -1);
                put("W", -7);
                put("Y", -6);
                put("V", -2);
                put("B", -2);
                put("Z", -1);
                put("X", -2);
                put("*", -8);
            }
        };

        HashMap<String, Integer> S = new HashMap<String, Integer>() {
            {
                put("A", 1);
                put("R", -1);
                put("N", 1);
                put("D", 0);
                put("C", 0);
                put("Q", -2);
                put("E", -1);
                put("G", 1);
                put("H", -2);
                put("I", -2);
                put("L", -4);
                put("K", -1);
                put("M", -2);
                put("F", -3);
                put("P", 1);
                put("S", 3);
                put("T", 2);
                put("W", -2);
                put("Y", -3);
                put("V", -2);
                put("B", 0);
                put("Z", -1);
                put("X", -1);
                put("*", -8);
            }
        };
        
        HashMap<String, Integer> T = new HashMap<String, Integer>() {
            {
                put("A",1);
                put("R",-2);
                put("N",0);
                put("D",-1);
                put("C",-3);
                put("Q",-2);
                put("E",-2);
                put("G",-1);
                put("H",-3);
                put("I",0);
                put("L",-3);
                put("K",-1);
                put("M",-1);
                put("F",-4);
                put("P",-1);
                put("S",2);
                put("T",4);
                put("W",-6);
                put("Y",-3);
                put("V",0);
                put("B",0);
                put("Z",-2);
                put("X",-1);
                put("*",-8);
            }
        };
        
        HashMap<String, Integer> W = new HashMap<String, Integer>() {
            {
                put("A",-7);
                put("R",1);
                put("N",-4);
                put("D",-8);
                put("C",-8);
                put("Q",-6);
                put("E",-8);
                put("G",-8);
                put("H",-3);
                put("I",-6);
                put("L",-3);
                put("K",-5);
                put("M",-6);
                put("F",-1);
                put("P",-7);
                put("S",-2);
                put("T",-6);
                put("W",12);
                put("Y",-2);
                put("V",-8);
                put("B",-6);
                put("Z",-7);
                put("X",-5);
                put("*",-8);
            }
        };
        
        HashMap<String, Integer> Y = new HashMap<String, Integer>() {
            {
                put("A",-4);
                put("R",-5);
                put("N",-2);
                put("D",-5);
                put("C",-1);
                put("Q",-5);
                put("E",-5);
                put("G",-6);
                put("H",-1);
                put("I",-2);
                put("L",-2);
                put("K",-5);
                put("M",-4);
                put("F",4);
                put("P",-6);
                put("S",-3);
                put("T",-3);
                put("W",-2);
                put("Y",8);
                put("V",-3);
                put("B",-3);
                put("Z",-5);
                put("X",-3);
                put("*",-8);
            }
        };
        
        HashMap<String, Integer> V = new HashMap<String, Integer>() {
            {
                put("A",0);
                put("R",-3);
                put("N",-3);
                put("D",-3);
                put("C",-3);
                put("Q",-3);
                put("E",-3);
                put("G",-2);
                put("H",-3);
                put("I",3);
                put("L",1);
                put("K",-4);
                put("M",1);
                put("F",-3);
                put("P",-2);
                put("S",-2);
                put("T",0);
                put("W",-8);
                put("Y",-3);
                put("V",5);
                put("B",-3);
                put("Z",-3);
                put("X",-1);
                put("*",-8);
            }
        };
        
        HashMap<String, Integer> B = new HashMap<String, Integer>() {
            {
                put("A",0);
                put("R",-2);
                put("N",3);
                put("D",4);
                put("C",-6);
                put("Q",0);
                put("E",3);
                put("G",0);
                put("H",1);
                put("I",-3);
                put("L",-4);
                put("K",0);
                put("M",-4);
                put("F",-5);
                put("P",-2);
                put("S",0);
                put("T",0);
                put("W",-6);
                put("Y",-3);
                put("V",-3);
                put("B",4);
                put("Z",2);
                put("X",-1);
                put("*",-8);
            }
        };
        
        HashMap<String, Integer> Z = new HashMap<String, Integer>() {
            {
                put("A",-1);
                put("R",-1);
                put("N",0);
                put("D",3);
                put("C",-7);
                put("Q",4);
                put("E",4);
                put("G",-2);
                put("H",1);
                put("I",-3);
                put("L",-3);
                put("K",-1);
                put("M",-2);
                put("F",-6);
                put("P",-1);
                put("S",-1);
                put("T",-2);
                put("W",-7);
                put("Y",-5);
                put("V",-3);
                put("B",2);
                put("Z",4);
                put("X",-1);
                put("*",-8);
            }
        };
        
        HashMap<String, Integer> X = new HashMap<String, Integer>() {
            {
                put("A",-1);
                put("R",-2);
                put("N",-1);
                put("D",-2);
                put("C",-4);
                put("Q",-1);
                put("E",-1);
                put("G",-2);
                put("H",-2);
                put("I",-1);
                put("L",-2);
                put("K",-2);
                put("M",-2);
                put("F",-3);
                put("P",-2);
                put("S",-1);
                put("T",-1);
                put("W",-5);
                put("Y",-3);
                put("V",-1);
                put("B",-1);
                put("Z",-1);
                put("X",-2);
                put("*",-8);
            }
        };
        
        HashMap<String, Integer> gap = new HashMap<String, Integer>() {
            {
                put("A",-8);
                put("R",-8);
                put("N",-8);
                put("D",-8);
                put("C",-8);
                put("Q",-8);
                put("E",-8);
                put("G",-8);
                put("H",-8);
                put("I",-8);
                put("L",-8);
                put("K",-8);
                put("M",-8);
                put("F",-8);
                put("P",-8);
                put("S",-8);
                put("T",-8);
                put("W",-8);
                put("Y",-8);
                put("V",-8);
                put("B",-8);
                put("Z",-8);
                put("X",-8);
                put("*",1);
            }
        };
        
        HashMap<String, HashMap<String, Integer>> pam = new HashMap<String, HashMap<String, Integer>>(){
            {
                put("A", A);
                put("R", R);
                put("N", N);
                put("D",D);
                put("C",C);
                put("Q",Q);
                put("E",E);
                put("G",G);
                put("H",H);
                put("I",I);
                put("L",L);
                put("K",K);
                put("M",M);
                put("F",F);
                put("P",P);
                put("S",S);
                put("T",T);
                put("W",W);
                put("Y",Y);
                put("V",V);
                put("B",B);
                put("Z",Z);
                put("X",X);
                put("*", gap);
            }
        };
        
        return pam;
    }

    public static HashMap<String, HashMap<String, Integer>> initializeBlosum() {
        HashMap<String, Integer> A = new HashMap<String, Integer>() {
            {
                put("A", 4);
                put("R", -1);
                put("N", -2);
                put("D", -2);
                put("C", 0);
                put("Q", -1);
                put("E", -1);
                put("G", 0);
                put("H", -2);
                put("I", -1);
                put("L", -1);
                put("K", -1);
                put("M", -1);
                put("F", -2);
                put("P", -1);
                put("S", 1);
                put("T", 0);
                put("W", -3);
                put("Y", -2);
                put("V", 0);
                put("B", -2);
                put("Z", -1);
                put("X", 0);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> R = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", 5);
                put("N", 0);
                put("D", -2);
                put("C", -3);
                put("Q", 1);
                put("E", 0);
                put("G", -2);
                put("H", 0);
                put("I", -3);
                put("L", -2);
                put("K", 2);
                put("M", -1);
                put("F", -3);
                put("P", -2);
                put("S", -1);
                put("T", -1);
                put("W", -3);
                put("Y", -2);
                put("V", -3);
                put("B", -1);
                put("Z", 0);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> N = new HashMap<String, Integer>() {
            {
                put("A", -2);
                put("R", 0);
                put("N", 6);
                put("D", 1);
                put("C", -3);
                put("Q", 0);
                put("E", 0);
                put("G", 0);
                put("H", 1);
                put("I", -3);
                put("L", -3);
                put("K", 0);
                put("M", -2);
                put("F", -3);
                put("P", -2);
                put("S", 1);
                put("T", 0);
                put("W", -4);
                put("Y", -2);
                put("V", -3);
                put("B", 3);
                put("Z", 0);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> D = new HashMap<String, Integer>() {
            {
                put("A", -2);
                put("R", -2);
                put("N", 1);
                put("D", 6);
                put("C", -3);
                put("Q", 0);
                put("E", 2);
                put("G", -1);
                put("H", -1);
                put("I", -3);
                put("L", -4);
                put("K", -1);
                put("M", -3);
                put("F", -3);
                put("P", -1);
                put("S", 0);
                put("T", -1);
                put("W", -4);
                put("Y", -3);
                put("V", -3);
                put("B", 4);
                put("Z", 1);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> C = new HashMap<String, Integer>() {
            {
                put("A", 0);
                put("R", -3);
                put("N", -3);
                put("D", -3);
                put("C", 9);
                put("Q", -3);
                put("E", -4);
                put("G", -3);
                put("H", -3);
                put("I", -1);
                put("L", -1);
                put("K", -3);
                put("M", -1);
                put("F", -2);
                put("P", -3);
                put("S", -1);
                put("T", -1);
                put("W", -2);
                put("Y", -2);
                put("V", -1);
                put("B", -3);
                put("Z", -3);
                put("X", -2);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> Q = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", 1);
                put("N", 0);
                put("D", 0);
                put("C", -3);
                put("Q", 5);
                put("E", 2);
                put("G", -2);
                put("H", 0);
                put("I", -3);
                put("L", -2);
                put("K", 1);
                put("M", 0);
                put("F", -3);
                put("P", -1);
                put("S", 0);
                put("T", -1);
                put("W", -2);
                put("Y", -1);
                put("V", -2);
                put("B", 0);
                put("Z", 3);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> E = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", 0);
                put("N", 0);
                put("D", 2);
                put("C", -4);
                put("Q", 2);
                put("E", 5);
                put("G", -2);
                put("H", 0);
                put("I", -3);
                put("L", -3);
                put("K", 1);
                put("M", -2);
                put("F", -3);
                put("P", -1);
                put("S", 0);
                put("T", -1);
                put("W", -3);
                put("Y", -2);
                put("V", -2);
                put("B", 1);
                put("Z", 4);
                put("X",-1 );
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> G = new HashMap<String, Integer>() {
            {
                put("A", 0);
                put("R", -2);
                put("N", 0);
                put("D", -1);
                put("C", -3);
                put("Q", -2);
                put("E", -2);
                put("G", 6);
                put("H", -2);
                put("I", -4);
                put("L", -4);
                put("K", -2);
                put("M", -3);
                put("F", -3);
                put("P", -2);
                put("S", 0);
                put("T", -2);
                put("W", -2);
                put("Y", -3);
                put("V", -3);
                put("B", -1);
                put("Z", -2);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> H = new HashMap<String, Integer>() {
            {
                put("A", -2);
                put("R", 0);
                put("N", 1);
                put("D", -1);
                put("C", -3);
                put("Q", 0);
                put("E", 0);
                put("G", -2);
                put("H", 8);
                put("I", -3);
                put("L", -3);
                put("K", -1);
                put("M", -2);
                put("F", -1);
                put("P", -2);
                put("S", -1);
                put("T", -2);
                put("W", -2);
                put("Y", 2);
                put("V", -3);
                put("B", 0);
                put("Z", 0);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> I = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", -3);
                put("N", -3);
                put("D", -3);
                put("C", -1);
                put("Q", -3);
                put("E", -3);
                put("G", -4);
                put("H", -3);
                put("I", 4);
                put("L",2 );
                put("K", -3);
                put("M", 1);
                put("F", 0);
                put("P", -3);
                put("S", -2);
                put("T", -1);
                put("W", -3);
                put("Y", -1);
                put("V", 3);
                put("B", -3);
                put("Z", -3);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> L = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", -2);
                put("N", -3);
                put("D", -4);
                put("C", -1);
                put("Q", -2);
                put("E", -3);
                put("G", -4);
                put("H", -3);
                put("I", 2);
                put("L", 4);
                put("K", -2);
                put("M", 2);
                put("F", 0);
                put("P", -3);
                put("S", -2);
                put("T", -1);
                put("W", -2);
                put("Y", -1);
                put("V", 1);
                put("B", -4);
                put("Z", -3);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> K = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", 2);
                put("N", 0);
                put("D", -1);
                put("C", -3);
                put("Q", 1);
                put("E", 1);
                put("G", -2);
                put("H", -1);
                put("I", -3);
                put("L", -2);
                put("K", 5);
                put("M", -1);
                put("F", -3);
                put("P", -1);
                put("S", 0);
                put("T", -1);
                put("W", -3);
                put("Y", -2);
                put("V", -2);
                put("B", 0);
                put("Z", 1);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> M = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", -1);
                put("N", -2);
                put("D", -3);
                put("C", -1);
                put("Q", 0);
                put("E", -2);
                put("G", -3);
                put("H", -2);
                put("I", 1);
                put("L", 2);
                put("K", -1);
                put("M", 5);
                put("F", 0);
                put("P", -2);
                put("S", -1);
                put("T", -1);
                put("W", -1);
                put("Y", -1);
                put("V", 1);
                put("B", 3);
                put("Z", -1);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> F = new HashMap<String, Integer>() {
            {
                put("A", -2);
                put("R", -3);
                put("N", -3);
                put("D", -3);
                put("C", -2);
                put("Q", -3);
                put("E", -3);
                put("G", -3);
                put("H", -1);
                put("I", 0);
                put("L", 0);
                put("K", -3);
                put("M", 0);
                put("F", 6);
                put("P", -4);
                put("S", -2);
                put("T", -2);
                put("W", 1);
                put("Y", 3);
                put("V", -1);
                put("B", -3);
                put("Z", -3);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> P = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", -2);
                put("N", -2);
                put("D", -1);
                put("C", -3);
                put("Q", -1);
                put("E", -1);
                put("G", -2);
                put("H", -2);
                put("I", -3);
                put("L", -3);
                put("K", -1);
                put("M", -2);
                put("F", -4);
                put("P", 7);
                put("S", -1);
                put("T", -1);
                put("W", -4);
                put("Y", -3);
                put("V", -2);
                put("B", -2);
                put("Z", -1);
                put("X", -2);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> S = new HashMap<String, Integer>() {
            {
                put("A", 1);
                put("R", -1);
                put("N", 1);
                put("D", 0);
                put("C", -1);
                put("Q", 0);
                put("E",0 );
                put("G", 0);
                put("H", -1);
                put("I", -2);
                put("L", -2);
                put("K", 0);
                put("M", -1);
                put("F", -2);
                put("P", -1);
                put("S", 4);
                put("T", 1);
                put("W", -3);
                put("Y", -2);
                put("V", -2);
                put("B", 0);
                put("Z", 0);
                put("X", 0);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> T = new HashMap<String, Integer>() {
            {
                put("A", 0);
                put("R", -1);
                put("N", 0);
                put("D", -1);
                put("C", -1);
                put("Q", -1);
                put("E", -1);
                put("G", -2);
                put("H", -2);
                put("I", -1);
                put("L", -1);
                put("K", -1);
                put("M", -1);
                put("F", -2);
                put("P", -1);
                put("S", 1);
                put("T", 5);
                put("W", -2);
                put("Y", -2);
                put("V", 0);
                put("B", -1);
                put("Z", -1);
                put("X", 0);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> W = new HashMap<String, Integer>() {
            {
                put("A", -3);
                put("R", -3);
                put("N", -4);
                put("D", -4);
                put("C", -2);
                put("Q", -2);
                put("E", -3);
                put("G", -2);
                put("H", -2);
                put("I", -3);
                put("L", -2);
                put("K", -3);
                put("M", -1);
                put("F", 1);
                put("P", -4);
                put("S", -3);
                put("T", -2);
                put("W", 11);
                put("Y", 2);
                put("V", -3);
                put("B", -4);
                put("Z", -3);
                put("X", -2);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> Y = new HashMap<String, Integer>() {
            {
                put("A", -2);
                put("R", -2);
                put("N", -2);
                put("D", -3);
                put("C", -2);
                put("Q", -1);
                put("E", -2);
                put("G", -3);
                put("H", 2);
                put("I", -1);
                put("L", -1);
                put("K", -2);
                put("M", -1);
                put("F", 3);
                put("P", -3);
                put("S", -2);
                put("T", -2);
                put("W", 2);
                put("Y", 7);
                put("V", -1);
                put("B", -3);
                put("Z", -2);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> V = new HashMap<String, Integer>() {
            {
                put("A", 0);
                put("R", -3);
                put("N", -3);
                put("D", -3);
                put("C", -1);
                put("Q", -2);
                put("E", -2);
                put("G", -3);
                put("H", -3);
                put("I", 3);
                put("L", 1);
                put("K", -2);
                put("M", 1);
                put("F", -1);
                put("P", -2);
                put("S", -2);
                put("T", 0);
                put("W", -3);
                put("Y", -1);
                put("V", 4);
                put("B", -3);
                put("Z", -2);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> B = new HashMap<String, Integer>() {
            {
                put("A", -2);
                put("R", -1);
                put("N", 3);
                put("D", 4);
                put("C", -3);
                put("Q", 0);
                put("E", 1);
                put("G", -1);
                put("H", 0);
                put("I", -3);
                put("L", -4);
                put("K", 0);
                put("M", -3);
                put("F", -3);
                put("P", -2);
                put("S", 0);
                put("T", -1);
                put("W", -4);
                put("Y", -3);
                put("V", -3);
                put("B", 4);
                put("Z", 1);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> Z = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", 0);
                put("N", 0);
                put("D", 1);
                put("C", -3);
                put("Q", 3);
                put("E", 4);
                put("G", -2);
                put("H", 0);
                put("I", -3);
                put("L", -3);
                put("K", 1);
                put("M", -1);
                put("F", -3);
                put("P", -1);
                put("S", 0);
                put("T", -1);
                put("W", -3);
                put("Y", -2);
                put("V", -2);
                put("B", 1);
                put("Z", 4);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> X = new HashMap<String, Integer>() {
            {
                put("A", 0);
                put("R", -1);
                put("N", -1);
                put("D", -1);
                put("C", -2);
                put("Q", -1);
                put("E", -1);
                put("G", -1);
                put("H", -1);
                put("I", -1);
                put("L", -1);
                put("K", -1);
                put("M", -1);
                put("F", -1);
                put("P", -2);
                put("S", 0);
                put("T", 0);
                put("W", -2);
                put("Y", -1);
                put("V", -1);
                put("B", -1);
                put("Z", -1);
                put("X", -1);
                put("*", -4);
            }
        };
        
        HashMap<String, Integer> gap = new HashMap<String, Integer>() {
            {
                put("A", -4);
                put("R", -4);
                put("N", -4);
                put("D", -4);
                put("C", -4);
                put("Q", -4);
                put("E", -4);
                put("G", -4);
                put("H", -4);
                put("I", -4);
                put("L", -4);
                put("K", -4);
                put("M", -4);
                put("F", -4);
                put("P", -4);
                put("S", -4);
                put("T", -4);
                put("W", -4);
                put("Y", -4);
                put("V", -4);
                put("B", -4);
                put("Z", -4);
                put("X", -4);
                put("*", 1);
            }
        };
        
        HashMap<String, HashMap<String, Integer>> blosum = new HashMap<String, HashMap<String, Integer>>(){
            {
                put("A", A);
                put("R", R);
                put("N", N);
                put("D",D);
                put("C",C);
                put("Q",Q);
                put("E",E);
                put("G",G);
                put("H",H);
                put("I",I);
                put("L",L);
                put("K",K);
                put("M",M);
                put("F",F);
                put("P",P);
                put("S",S);
                put("T",T);
                put("W",W);
                put("Y",Y);
                put("V",V);
                put("B",B);
                put("Z",Z);
                put("X",X);
                put("*", gap);
            }
        };
        
        return blosum;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
