import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GUI extends JFrame {
    private JLabel info = new JLabel("Info: ");
    private JTextField text_box = new JTextField(12);
    private JLabel caret_info = new JLabel("Caret info will be displayed here");
    private JLabel change_info = new JLabel("Change info will be displayed here");
    private JLabel undo_info = new JLabel("Undo info will be displayed here");
    private JLabel enter_info = new JLabel();
    private JButton go_button = new JButton("Go!");
    private JButton undo_button = new JButton("Undo");
    private JButton redo_button = new JButton("Redo");
    protected UndoManager undo = new UndoManager(); /* manager for undo/redo support */
    public GUI() {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout(FlowLayout.LEADING));
        frame.add(text_box);
        frame.add(go_button);
        frame.add(info);
        frame.add(caret_info);
        frame.add(change_info);
        frame.add(enter_info);
        frame.add(enter_info);
        frame.add(undo_button);
        frame.add(redo_button);
        frame.setTitle("Textbox");
        frame.setSize(225, 170);
        frame.setComponentOrientation(
                ComponentOrientation.UNKNOWN);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE); /* so process finishes when user closes window */
        frame.setVisible(true); /* makes frame visible */
        pack(); /* figures everything out, basically */
        frame.setLocationRelativeTo(null); /* centers on screen (must be after pack statement!!!) */
        text_box.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                int dot = e.getDot(); /* caret position where cursor started */
                int mark = e.getMark(); /* caret position where cursor ended */
                if (dot == mark) /* no selection */
                    setCaretInfo("Caret at " + dot + " (no selection)");
                else if (mark > dot) {
                    String selection = getText().substring(dot, mark);
                    setCaretInfo("Selected \"" + selection + "\"");
                } else {
                    String selection = getText().substring(mark, dot);
                    setCaretInfo("Selected \"" + selection + "\"");
                }
            }
        });
        text_box.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                change_info.setText(e.getLength() + " character(s) inserted");
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                change_info.setText(e.getLength() + " character(s) removed");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                /* only used in styled components */
            }
        });
        text_box.addActionListener(new ActionListener() { /* also check out JRootPane */
            @Override
            public void actionPerformed(ActionEvent e) {
                change_info.setText("Enter key pressed");
            }
        });
        go_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                change_info.setText("Button pressed");
            }
        });
        text_box.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undo.addEdit(e.getEdit());
               // undoAction.updateUndoState();
               // redoAction.updateRedoState();
            }
        });
        undo_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undo.undo();
                    undo_info.setText("Undid");
                } catch (CannotUndoException ex) {
                    undo_info.setText("Unable to undo: " + ex);
                    ex.printStackTrace();
                }
            }
        });
        redo_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undo.redo();
                } catch (CannotRedoException ex) {
                    undo_info.setText("Unable to redo: " + ex);
                    ex.printStackTrace();
                }
            }
        });
    }
    public void setCaretInfo(String s) {caret_info.setText(s);}
    public String getText() {return text_box.getText();}
    public static void main(String[] args) {
        GUI gui = new GUI();
    }

}
