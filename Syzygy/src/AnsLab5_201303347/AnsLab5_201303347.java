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
        matrix = initializeMatrix(sequence1.length, sequence2.length);

        fillMatrix(matrix, new int[]{2, -1, -2});
        printMatrix(matrix);
        backtrack(matrix[sequence1.length - 1][sequence2.length - 1], 1, 1, "", "");
        printAlignment(sequence1, sequence2);
        countFrequencies(sequence1, sequence2);
        printFrequencies();

//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new AnsLab5_201303347().setVisible(true);
//            }
//        });
    }

    public static void fillMatrix(Cell[][] matrix, int[] scoring) {
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

    public static void backtrack(Cell cell, int s1, int s2, String seq1, String seq2) {
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
            backtrack(cell.diagonal, s1 + 1, s2 + 1, seq1, seq2);
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
            backtrack(cell.left, s1 + 1, s2, seq1, seq2);
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
            backtrack(cell.top, s1, s2 + 1, seq1, seq2);
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
        
        keys.replaceAll(".(?=.)", "$0");
        System.out.println(keys);
        System.out.println(frequencies);
        keys = "";
        frequencies = "";
        
        while (f2.hasNext()) {
            Map.Entry pair = (Map.Entry) f2.next();
            keys = keys + pair.getKey();
            frequencies = frequencies + pair.getValue();
            f2.remove();
        }
        
        System.out.println(keys);
        System.out.println(frequencies);
    }

    public static void printMatrix(Cell[][] matrix) {
        String format = "%-3s";

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

    public static Cell[][] initializeMatrix(int m, int n) {
        Cell[][] matrix = new Cell[m][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[j][i] = new Cell(0);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
