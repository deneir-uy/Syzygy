package AnsLab5_201303347;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author deneir-uy
 */
class Sequence {

    String alignment;
    String sequence;
    String description;
    int length;

    Sequence(String sequence, String description) {
        this.alignment = "";
        this.description = description;
        this.sequence = sequence.trim();
        this.length = this.sequence.length() + 1;
    }

}

class Cell {

    Cell diagonal;
    Cell left;
    Cell top;
    int i;
    int j;
    int value;

    Cell(int value, int i, int j) {
        this.diagonal = null;
        this.left = null;
        this.top = null;
        this.i = i;
        this.j = j;
        this.value = value;
    }

    final int max(Cell diagonal, Cell left, Cell top, int score, int gap, int base) {
        int diagonalSum = diagonal.value + score;
        int leftSum = left.value + gap;
        int topSum = top.value + gap;
        int max = base;

        if (diagonalSum > max) {
            max = diagonalSum;
        }

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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        flechFasta = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtarInput = new javax.swing.JTextArea();
        btnReset = new javax.swing.JButton();
        btnUpload = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
        rdbNucleo = new javax.swing.JRadioButton();
        rdbProtein = new javax.swing.JRadioButton();
        lblScheme = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spnMatch = new javax.swing.JSpinner();
        spnMismatch = new javax.swing.JSpinner();
        spnGap = new javax.swing.JSpinner();
        lblMatrix = new javax.swing.JLabel();
        rdbPam = new javax.swing.JRadioButton();
        rdbBlosum = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Pairwise Sequence Alignment by Deneir Uy"); // NOI18N

        txtarInput.setColumns(20);
        txtarInput.setRows(5);
        jScrollPane1.setViewportView(txtarInput);

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnUpload.setText("Upload File");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbNucleo);
        rdbNucleo.setSelected(true);
        rdbNucleo.setText("Nucleotide Alignment");
        rdbNucleo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbNucleoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbProtein);
        rdbProtein.setText("Protein Alignment");
        rdbProtein.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbProteinActionPerformed(evt);
            }
        });

        lblScheme.setText("Scoring Scheme");

        jLabel2.setText("Match");

        jLabel3.setText("Mismatch");

        jLabel4.setText("Gap");

        spnMatch.setValue(1);

        spnGap.setValue(-1);

        lblMatrix.setText("Scoring Matrix");
        lblMatrix.setEnabled(false);

        buttonGroup2.add(rdbPam);
        rdbPam.setSelected(true);
        rdbPam.setText("Pam120 (Global)");
        rdbPam.setEnabled(false);

        buttonGroup2.add(rdbBlosum);
        rdbBlosum.setText("Blosum62 (Local)");
        rdbBlosum.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblScheme)
                                    .addComponent(rdbNucleo)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(spnMatch, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                            .addComponent(spnMismatch)
                                            .addComponent(spnGap)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblMatrix)
                                .addComponent(rdbProtein))
                            .addComponent(rdbPam, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbBlosum, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnUpload)
                    .addComponent(btnSubmit))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbNucleo)
                    .addComponent(rdbProtein))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblScheme)
                    .addComponent(lblMatrix))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spnMatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbPam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spnMismatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbBlosum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spnGap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        int score;
        readInput(txtarInput.getText());
        String text = "";

        if (rdbNucleo.isSelected()) {
            frequency1 = initializeFrequencies(true);
            frequency2 = initializeFrequencies(true);
            matrix = initializeMatrix(sequence1.length, sequence2.length, true, (Integer) spnGap.getValue());
            fillMatrixNucleotide(matrix, new int[]{(Integer) spnMatch.getValue(), (Integer) spnMismatch.getValue(), (Integer) spnGap.getValue()});
            backtrack(matrix[sequence1.length - 1][sequence2.length - 1], 1, 1, "", "");
            score = matrix[sequence1.sequence.length()][sequence2.sequence.length()].value;
            text = "======== Nucleotide Global Pairwide Sequence Alignment ========";
            //System.out.println("======== Nucleotide Global Pairwide Sequence Alignment ========");
        } else if (rdbPam.isSelected()) {
            pam = initializePam();
            frequency1 = initializeFrequencies(false);
            frequency2 = initializeFrequencies(false);
            matrix = initializeMatrix(sequence1.length, sequence2.length, true, -8);
            globalFillMatrixProtein(matrix);
            backtrack(matrix[sequence1.length - 1][sequence2.length - 1], 1, 1, "", "");
            score = matrix[sequence1.sequence.length()][sequence2.sequence.length()].value;
            text = "======== Protein Global Pairwide Sequence Alignment (PAM120) ========";
            //System.out.println("======== Protein Global Pairwide Sequence Alignment (PAM120) ========");
        } else {
            ArrayList<Cell> cellArray = new ArrayList<>();
            blosum = initializeBlosum();
            frequency1 = initializeFrequencies(false);
            frequency2 = initializeFrequencies(false);
            matrix = initializeMatrix(sequence1.length, sequence2.length, false, -4);
            localFillMatrixProtein(matrix);
            score = findMaxScore(matrix);
            cellArray = findMaxCell(score);
            for (int i = 0; i < cellArray.size(); i++) {
                backtrack(cellArray.get(i), sequence1.length - cellArray.get(i).j, sequence2.length - cellArray.get(i).i, "", "");
            }
            text = "======== Protein Local Pairwide Sequence Alignment (BLOSUM62) ========";
            //System.out.println("======== Protein Local Pairwide Sequence Alignment (BLOSUM62) ========");
        }

        //printMatrix(matrix);
        countFrequencies(sequence1, sequence2);
        displayOutput(score, text);
        //printOutput(score, text);

        frequency1.clear();
        frequency2.clear();
        alignments.clear();
        sequence1 = new Sequence("", "");
        sequence2 = new Sequence("", "");
        matrix = initializeMatrix(0, 0, true, 0);
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void rdbProteinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbProteinActionPerformed
        spnMatch.setEnabled(false);
        spnMismatch.setEnabled(false);
        spnGap.setEnabled(false);
        lblScheme.setEnabled(false);
        lblMatrix.setEnabled(true);
        rdbPam.setEnabled(true);
        rdbBlosum.setEnabled(true);
    }//GEN-LAST:event_rdbProteinActionPerformed

    private void rdbNucleoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbNucleoActionPerformed
        spnMatch.setEnabled(true);
        spnMismatch.setEnabled(true);
        spnGap.setEnabled(true);
        lblScheme.setEnabled(true);
        lblMatrix.setEnabled(false);
        rdbPam.setEnabled(false);
        rdbBlosum.setEnabled(false);
    }//GEN-LAST:event_rdbNucleoActionPerformed

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        flechFasta.showOpenDialog(jScrollPane1);
        File fasta = flechFasta.getSelectedFile();
        String fastaContents = "";

        try {
            fastaContents = readFasta(fasta);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnsLab5_201303347.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtarInput.setText(fastaContents);
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        this.dispose();
        new AnsLab5_201303347().setVisible(true);
    }//GEN-LAST:event_btnResetActionPerformed

    public String readFasta(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        String contents = "";

        while (scan.hasNextLine()) {
            contents += scan.nextLine() + "\n";
        }

        return contents;
    }

    public static void readInput(String input) {
        ArrayList<String> sequences = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        Scanner scan = new Scanner(input);
        String sequenceString = "";
        String line;

        while (scan.hasNextLine()) {
            line = scan.nextLine();

            if (!line.isEmpty()) {
                if (line.contains(">")) {
                    sequences.add(sequenceString);
                    descriptions.add(line);
                    sequenceString = "";
                } else {
                    sequenceString += line;
                }
            }
        }

        sequences.add(sequenceString);

        for (int i = 0; i < sequences.size(); i++) {
            if (sequences.get(i).isEmpty()) {
                sequences.remove(i);
            }
        }

        sequence1 = new Sequence(sequences.get(0), descriptions.get(0));
        sequence2 = new Sequence(sequences.get(1), descriptions.get(1));
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
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

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnsLab5_201303347().setVisible(true);
            }
        });
    }

    public static int findMaxScore(Cell[][] matrix) {
        Cell currentCell;
        int max = -9999;

        for (int i = 1; i < sequence2.length; i++) {
            for (int j = 1; j < sequence1.length; j++) {
                currentCell = matrix[j][i];

                if (currentCell.value > max) {
                    max = currentCell.value;
                }
            }
        }

        return max;
    }

    public static ArrayList<Cell> findMaxCell(int max) {
        ArrayList<Cell> cellArray = new ArrayList<Cell>();
        Cell currentCell;

        for (int i = 1; i < sequence2.length; i++) {
            for (int j = 1; j < sequence1.length; j++) {
                currentCell = matrix[j][i];

                if (currentCell.value == max) {
                    cellArray.add(currentCell);
                }
            }
        }

        return cellArray;
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
                            matrix[j - 1][i], matrix[j][i - 1], match, gap, -9999);
                } else {
                    currentCell.value = currentCell.max(matrix[j - 1][i - 1],
                            matrix[j - 1][i], matrix[j][i - 1], mismatch, gap, -9999);
                }

                matrix[j][i] = currentCell;
            }
        }
    }

    public static void localFillMatrixProtein(Cell[][] matrix) {
        Cell currentCell;

        for (int i = 1; i < sequence2.length; i++) {
            for (int j = 1; j < sequence1.length; j++) {
                currentCell = matrix[j][i];

                currentCell.value = currentCell.max(matrix[j - 1][i - 1], matrix[j - 1][i], matrix[j][i - 1], blosum.get(sequence1.sequence.substring(j - 1, j)).get(sequence2.sequence.substring(i - 1, i)), -4, 0);

                matrix[j][i] = currentCell;
            }
        }
    }

    public static void globalFillMatrixProtein(Cell[][] matrix) {
        Cell currentCell;

        for (int i = 1; i < sequence2.length; i++) {
            for (int j = 1; j < sequence1.length; j++) {
                currentCell = matrix[j][i];

                currentCell.value = currentCell.max(matrix[j - 1][i - 1], matrix[j - 1][i], matrix[j][i - 1], pam.get(sequence1.sequence.substring(j - 1, j)).get(sequence2.sequence.substring(i - 1, i)), -8, -9999);

                matrix[j][i] = currentCell;
            }
        }
    }

    public static void backtrack(Cell cell, int s1, int s2, String seq1, String seq2) {
        boolean test = false;

        if (cell.diagonal != null) {
            if (test == true) {
                seq1 = seq1.substring(1);
                seq2 = seq2.substring(1);
            }

            seq1 = sequence1.sequence.substring(
                    sequence1.sequence.length() - s1, sequence1.sequence.
                    length() - s1 + 1) + seq1;
            seq2 = sequence2.sequence.substring(
                    sequence2.sequence.length() - s2, sequence2.sequence.
                    length() - s2 + 1) + seq2;
            backtrack(cell.diagonal, s1 + 1, s2 + 1, seq1, seq2);
            test = true;
        }

        if (cell.left != null) {
            if (test == true) {
                seq1 = seq1.substring(1);
                seq2 = seq2.substring(1);
            }

            seq1 = sequence1.sequence.substring(
                    sequence1.sequence.length() - s1, sequence1.sequence.
                    length() - s1 + 1) + seq1;
            seq2 = "-" + seq2;
            backtrack(cell.left, s1 + 1, s2, seq1, seq2);
            test = true;
        }

        if (cell.top != null) {
            if (test == true) {
                seq1 = seq1.substring(1);
                seq2 = seq2.substring(1);
            }

            seq1 = "-" + seq1;
            seq2 = sequence2.sequence.substring(
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

            if (frequency1.containsKey(key)) {
                frequency1.put(key, frequency1.get(key) + 1);
            } else {
                frequency1.put(key, 1);
            }

        }

        for (int i = 0; i < sequence2.sequence.length(); i++) {
            key = sequence2.sequence.substring(i, i + 1);

            if (frequency2.containsKey(key)) {
                frequency2.put(key, frequency2.get(key) + 1);
            } else {
                frequency2.put(key, 1);
            }
        }
    }

    public static String printFrequencies() {
        String format = "%-4s";
        String keys1 = "";
        String keys2 = "";
        String frequencies1 = "";
        String frequencies2 = "";
        //String combinedFrequencies = "";
        Iterator f1 = frequency1.entrySet().iterator();
        Iterator f2 = frequency2.entrySet().iterator();

        while (f1.hasNext()) {
            Map.Entry pair = (Map.Entry) f1.next();
            keys1 = keys1 + pair.getKey();
            frequencies1 = frequencies1 + pair.getValue();
            f1.remove();
        }

        keys1 = keys1.replaceAll(".(?=.)", String.format(format, "$0"));
        frequencies1 = frequencies1.replaceAll(".(?=.)", String.format(format, "$0"));
//        System.out.println(keys1 + "    #");
//        System.out.println(frequencies1 + "    " + sequence1.length);
//        System.out.println("");

        while (f2.hasNext()) {
            Map.Entry pair = (Map.Entry) f2.next();
            keys2 = keys2 + pair.getKey();
            frequencies2 = frequencies2 + pair.getValue();
            f2.remove();
        }

        keys2 = keys2.replaceAll(".(?=.)", String.format(format, "$0"));
        frequencies2 = frequencies2.replaceAll(".(?=.)", String.format(format, "$0"));
//        System.out.println(keys2 + "    #");
//        System.out.println(frequencies2 + "    " + sequence2.length);

        return sequence1.description + "\n" + keys1 + "    #\n" + frequencies1 + "    " + sequence1.length
                + "\n\n" + sequence2.description + "\n" + keys2 + "    #\n" + frequencies2 + "    " + sequence2.length;
    }

    public static void printOutput(int score, String text) {
        System.out.println(text);
        System.out.println("Pairwise Sequence Alignment ver. 1.0 by Deneir Uy (2013-03347)");
        System.out.println("Run date: " + new Date());
        System.out.println("Submitted sequences:");
        System.out.println(sequence1.description + "\n" + formatString(sequence1.sequence));
        System.out.println("");
        System.out.println(sequence2.description + "\n" + formatString(sequence2.sequence));
        System.out.println("");
        System.out.println("Sequence1 length: " + sequence1.sequence.length());
        System.out.println("Sequence1 length: " + sequence2.sequence.length());
        printFrequencies();
        System.out.println("");
        System.out.println("Optimal alignment result(s):");
        printAlignment(sequence1, sequence2);
        System.out.println("Score: " + score);
    }

    public static void displayOutput(int score, String text) {
        JFrame frame = new JFrame("Optimal Alignment");
        JTextArea txtarFile = new JTextArea(40, 55);
        JScrollPane scrlpaneFile = new JScrollPane(txtarFile);
        JPanel panel = new JPanel();
        JButton save = new JButton("Save as Text");

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.setLayout(new GridLayout(1, 0));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.add(scrlpaneFile);
        panel.add(save);
        frame.add(panel);
        txtarFile.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        txtarFile.setEditable(false);

        txtarFile.append(text + "\n");
        txtarFile.append("Pairwise Sequence Alignment ver. 1.0 by Deneir Uy (2013-03347)\n");
        txtarFile.append("Run date: " + new Date() + "\n\n");
        txtarFile.append("Submitted sequences:\n");
        txtarFile.append(sequence1.description + "\n" + formatString(sequence1.sequence) + "\n\n");
        txtarFile.append(sequence2.description + "\n" + formatString(sequence2.sequence) + "\n\n");
        txtarFile.append("Sequence1 length: " + sequence1.sequence.length() + "\n");
        txtarFile.append("Sequence1 length: " + sequence2.sequence.length() + "\n\n");
        txtarFile.append(printFrequencies() + "\n\n");
        txtarFile.append("Optimal alignment result(s):\n");
        displayAlignment(sequence1, sequence2, txtarFile);
        txtarFile.append("\n" + "Score: " + score);
        
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveOutput(txtarFile.getText());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AnsLab5_201303347.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    public static void saveOutput(String text) throws FileNotFoundException {
        JFileChooser choose = new JFileChooser();
        choose.showOpenDialog(new JScrollPane());
        
        File file = choose.getSelectedFile();
        PrintWriter out = new PrintWriter(file);
        
        out.println(text);
        out.close();
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
        String matches = "";
        int k = 0;

        for (int i = 0; i < alignments.size(); i++) {
            for (int j = 0; j < alignments.get(i)[0].length(); j++, k++) {
                if (k == 10) {
                    matches += " ";
                    k = 0;
                }

                if (alignments.get(i)[0].substring(j, j + 1).equals("-") || alignments.get(i)[1].substring(j, j + 1).equals("-")) {
                    matches += " ";
                } else if (alignments.get(i)[0].substring(j, j + 1).matches(alignments.get(i)[1].substring(j, j + 1))) {
                    matches += "*";
                } else {
                    matches += ".";
                }
            }

            System.out.println(formatString(alignments.get(i)[0]));
            System.out.println(matches);
            System.out.println(formatString(alignments.get(i)[1]));
            System.out.println("");
            matches = "";
            k = 0;

        }

    }

    public static void displayAlignment(Sequence s1, Sequence s2, JTextArea txtarFile) {
        String matches = "";
        int k = 0;

        for (int i = 0; i < alignments.size(); i++) {
            for (int j = 0; j < alignments.get(i)[0].length(); j++, k++) {
                if (k == 10) {
                    matches += " ";
                    k = 0;
                }

                if (alignments.get(i)[0].substring(j, j + 1).equals("-") || alignments.get(i)[1].substring(j, j + 1).equals("-")) {
                    matches += " ";
                } else if (alignments.get(i)[0].substring(j, j + 1).matches(alignments.get(i)[1].substring(j, j + 1))) {
                    matches += "*";
                } else {
                    matches += ".";
                }
            }

            txtarFile.append(formatString(alignments.get(i)[0]));
            txtarFile.append("\n");
            txtarFile.append(matches);
            txtarFile.append("\n");
            txtarFile.append(formatString(alignments.get(i)[1]));
            txtarFile.append("\n\n");
            matches = "";
            k = 0;

        }

    }

    public static String formatString(String alignment) {
        String formattedAlignment = "";
        int k = 0;
        for (int i = 0; i < alignment.length(); i++, k++) {
            if (k == 10) {
                formattedAlignment += " ";
                k = 0;
            }
            formattedAlignment += alignment.substring(i, i + 1);
        }

        return formattedAlignment;
    }

    public static Cell[][] initializeMatrix(int m, int n, boolean isGlobal, int gap) {
        Cell[][] matrix = new Cell[m][n];

        if (isGlobal) {
            for (int i = 0; i < n; i++) {
                matrix[0][i] = new Cell(i * gap, i, 0);
            }

            for (int i = 0; i < m; i++) {
                matrix[i][0] = new Cell(i * gap, 0, i);
            }

            for (int i = 1; i < n; i++) {
                for (int j = 1; j < m; j++) {
                    matrix[j][i] = new Cell(0, i, j);
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                matrix[0][i] = new Cell(0, i, 0);
            }

            for (int i = 0; i < m; i++) {
                matrix[i][0] = new Cell(0, 0, i);
            }

            for (int i = 1; i < n; i++) {
                for (int j = 1; j < m; j++) {
                    matrix[j][i] = new Cell(0, i, j);
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
            map.put("R", 0);
            map.put("N", 0);
            map.put("D", 0);
            map.put("C", 0);
            map.put("Q", 0);
            map.put("E", 0);
            map.put("G", 0);
            map.put("H", 0);
            map.put("I", 0);
            map.put("L", 0);
            map.put("K", 0);
            map.put("M", 0);
            map.put("F", 0);
            map.put("P", 0);
            map.put("S", 0);
            map.put("T", 0);
            map.put("W", 0);
            map.put("Y", 0);
            map.put("V", 0);
            map.put("B", 0);
            map.put("Z", 0);
            map.put("X", 0);
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
                put("A", 1);
                put("R", -2);
                put("N", 0);
                put("D", -1);
                put("C", -3);
                put("Q", -2);
                put("E", -2);
                put("G", -1);
                put("H", -3);
                put("I", 0);
                put("L", -3);
                put("K", -1);
                put("M", -1);
                put("F", -4);
                put("P", -1);
                put("S", 2);
                put("T", 4);
                put("W", -6);
                put("Y", -3);
                put("V", 0);
                put("B", 0);
                put("Z", -2);
                put("X", -1);
                put("*", -8);
            }
        };

        HashMap<String, Integer> W = new HashMap<String, Integer>() {
            {
                put("A", -7);
                put("R", 1);
                put("N", -4);
                put("D", -8);
                put("C", -8);
                put("Q", -6);
                put("E", -8);
                put("G", -8);
                put("H", -3);
                put("I", -6);
                put("L", -3);
                put("K", -5);
                put("M", -6);
                put("F", -1);
                put("P", -7);
                put("S", -2);
                put("T", -6);
                put("W", 12);
                put("Y", -2);
                put("V", -8);
                put("B", -6);
                put("Z", -7);
                put("X", -5);
                put("*", -8);
            }
        };

        HashMap<String, Integer> Y = new HashMap<String, Integer>() {
            {
                put("A", -4);
                put("R", -5);
                put("N", -2);
                put("D", -5);
                put("C", -1);
                put("Q", -5);
                put("E", -5);
                put("G", -6);
                put("H", -1);
                put("I", -2);
                put("L", -2);
                put("K", -5);
                put("M", -4);
                put("F", 4);
                put("P", -6);
                put("S", -3);
                put("T", -3);
                put("W", -2);
                put("Y", 8);
                put("V", -3);
                put("B", -3);
                put("Z", -5);
                put("X", -3);
                put("*", -8);
            }
        };

        HashMap<String, Integer> V = new HashMap<String, Integer>() {
            {
                put("A", 0);
                put("R", -3);
                put("N", -3);
                put("D", -3);
                put("C", -3);
                put("Q", -3);
                put("E", -3);
                put("G", -2);
                put("H", -3);
                put("I", 3);
                put("L", 1);
                put("K", -4);
                put("M", 1);
                put("F", -3);
                put("P", -2);
                put("S", -2);
                put("T", 0);
                put("W", -8);
                put("Y", -3);
                put("V", 5);
                put("B", -3);
                put("Z", -3);
                put("X", -1);
                put("*", -8);
            }
        };

        HashMap<String, Integer> B = new HashMap<String, Integer>() {
            {
                put("A", 0);
                put("R", -2);
                put("N", 3);
                put("D", 4);
                put("C", -6);
                put("Q", 0);
                put("E", 3);
                put("G", 0);
                put("H", 1);
                put("I", -3);
                put("L", -4);
                put("K", 0);
                put("M", -4);
                put("F", -5);
                put("P", -2);
                put("S", 0);
                put("T", 0);
                put("W", -6);
                put("Y", -3);
                put("V", -3);
                put("B", 4);
                put("Z", 2);
                put("X", -1);
                put("*", -8);
            }
        };

        HashMap<String, Integer> Z = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", -1);
                put("N", 0);
                put("D", 3);
                put("C", -7);
                put("Q", 4);
                put("E", 4);
                put("G", -2);
                put("H", 1);
                put("I", -3);
                put("L", -3);
                put("K", -1);
                put("M", -2);
                put("F", -6);
                put("P", -1);
                put("S", -1);
                put("T", -2);
                put("W", -7);
                put("Y", -5);
                put("V", -3);
                put("B", 2);
                put("Z", 4);
                put("X", -1);
                put("*", -8);
            }
        };

        HashMap<String, Integer> X = new HashMap<String, Integer>() {
            {
                put("A", -1);
                put("R", -2);
                put("N", -1);
                put("D", -2);
                put("C", -4);
                put("Q", -1);
                put("E", -1);
                put("G", -2);
                put("H", -2);
                put("I", -1);
                put("L", -2);
                put("K", -2);
                put("M", -2);
                put("F", -3);
                put("P", -2);
                put("S", -1);
                put("T", -1);
                put("W", -5);
                put("Y", -3);
                put("V", -1);
                put("B", -1);
                put("Z", -1);
                put("X", -2);
                put("*", -8);
            }
        };

        HashMap<String, Integer> gap = new HashMap<String, Integer>() {
            {
                put("A", -8);
                put("R", -8);
                put("N", -8);
                put("D", -8);
                put("C", -8);
                put("Q", -8);
                put("E", -8);
                put("G", -8);
                put("H", -8);
                put("I", -8);
                put("L", -8);
                put("K", -8);
                put("M", -8);
                put("F", -8);
                put("P", -8);
                put("S", -8);
                put("T", -8);
                put("W", -8);
                put("Y", -8);
                put("V", -8);
                put("B", -8);
                put("Z", -8);
                put("X", -8);
                put("*", 1);
            }
        };

        HashMap<String, HashMap<String, Integer>> pam = new HashMap<String, HashMap<String, Integer>>() {
            {
                put("A", A);
                put("R", R);
                put("N", N);
                put("D", D);
                put("C", C);
                put("Q", Q);
                put("E", E);
                put("G", G);
                put("H", H);
                put("I", I);
                put("L", L);
                put("K", K);
                put("M", M);
                put("F", F);
                put("P", P);
                put("S", S);
                put("T", T);
                put("W", W);
                put("Y", Y);
                put("V", V);
                put("B", B);
                put("Z", Z);
                put("X", X);
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
                put("X", -1);
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
                put("L", 2);
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
                put("E", 0);
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

        HashMap<String, HashMap<String, Integer>> blosum = new HashMap<String, HashMap<String, Integer>>() {
            {
                put("A", A);
                put("R", R);
                put("N", N);
                put("D", D);
                put("C", C);
                put("Q", Q);
                put("E", E);
                put("G", G);
                put("H", H);
                put("I", I);
                put("L", L);
                put("K", K);
                put("M", M);
                put("F", F);
                put("P", P);
                put("S", S);
                put("T", T);
                put("W", W);
                put("Y", Y);
                put("V", V);
                put("B", B);
                put("Z", Z);
                put("X", X);
                put("*", gap);
            }
        };

        return blosum;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnUpload;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JFileChooser flechFasta;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMatrix;
    private javax.swing.JLabel lblScheme;
    private javax.swing.JRadioButton rdbBlosum;
    private javax.swing.JRadioButton rdbNucleo;
    private javax.swing.JRadioButton rdbPam;
    private javax.swing.JRadioButton rdbProtein;
    private javax.swing.JSpinner spnGap;
    private javax.swing.JSpinner spnMatch;
    private javax.swing.JSpinner spnMismatch;
    private javax.swing.JTextArea txtarInput;
    // End of variables declaration//GEN-END:variables
}
