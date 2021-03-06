/* A test of various button and action listeners in Swing. The code hasn't been cleaned up yet, but I
have tried to comment thoroughly throughout. Keep in mind CTRL-Z (undo) and CTRL-Y (redo) key bindings
have also been implemented. */
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends JFrame {
        public GUI() {
        Dimension label_dimension = new Dimension(215, 15); /* sets dimensions of each JLabel so they don't move around */
        caret_info.setPreferredSize(label_dimension);
        change_info.setPreferredSize(label_dimension);
        undo_info.setPreferredSize(label_dimension);
        enter_info.setPreferredSize(label_dimension);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout(FlowLayout.LEFT)); /* left adjustment of components */
        frame.add(text_box);
        text_box.requestFocusInWindow(); /* gives text_box focus on start (cursor starts there) */
        frame.add(go_button);
        frame.add(info);
        frame.add(caret_info);
        frame.add(change_info);
        frame.add(enter_info);
        frame.add(enter_info);
        frame.add(undo_info);
        frame.add(undo_button);
        frame.add(redo_button);
        frame.setTitle("Textbox");
        frame.setSize(225, 205);
        //frame.setComponentOrientation(
        //        ComponentOrientation.UNKNOWN);
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
                    caret_info.setText("Caret at " + dot + " (no selection)");
                else if (mark > dot) { /* user selected from left to right */
                    String selection = getText().substring(dot, mark);
                    caret_info.setText("Selected \"" + selection + "\"");
                } else { /* user selected from right to left */
                    String selection = getText().substring(mark, dot);
                    caret_info.setText("Selected \"" + selection + "\"");
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
            public void changedUpdate(DocumentEvent e) {} /* only used in styled components */
        });
        text_box.addActionListener(new ActionListener() { /* listens for user to press Enter key while
        text_box has focus */      // also check out JRootPane
            @Override
            public void actionPerformed(ActionEvent e) {
                enter_info.setText("Enter key pressed");
                //go_button.doClick(); /* simulates user clicking button */
            }
        });
        go_button.addActionListener(new ActionListener() { /* listens for button to be clicked */
            @Override
            public void actionPerformed(ActionEvent e) {
                enter_info.setText("Button pressed");
            }
        });
        text_box.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undo.addEdit(e.getEdit());
                undo_info.setText("Registered undoable event");
               // undoAction.updateUndoState(); /* used for menus(?) */
               // redoAction.updateRedoState();
            }
        });
        undo_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undo.undo();
                    undo_info.setText("Undo performed");
                } catch (CannotUndoException ex) {
                    undo_info.setText("Unable to undo");
                    ex.printStackTrace();
                }
            }
        });
        redo_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undo.redo();
                    undo_info.setText("Redo performed");
                } catch (CannotRedoException ex) {
                    undo_info.setText("Unable to redo");
                    ex.printStackTrace();
                }
            }
        });
        text_box.addKeyListener(new KeyListener() { // not sure if this is the best way to do this
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                /* check to see if 'Z' was pressed as well as CTRL */
                if((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
                    undo_button.doClick(); /* click undo button */
                /* check to see if 'Y' was pressed as well as CTRL */
                else if((e.getKeyCode() == KeyEvent.VK_Y) && ((e.getModifiers() * KeyEvent.CTRL_MASK) != 0))
                    redo_button.doClick(); /* click redo button (CTRL-Y) */
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    public String getText() {return text_box.getText();}
    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    private JLabel info = new JLabel("Info: ");
    private JTextField text_box = new JTextField(12);
    private JLabel caret_info = new JLabel("Caret info will be displayed here");
    private JLabel change_info = new JLabel("Change info will be displayed here");
    private JLabel undo_info = new JLabel("Undo info will be displayed here");
    private JLabel enter_info = new JLabel("Listening for Button/Enter key...");
    private JButton go_button = new JButton("Go!");
    private JButton undo_button = new JButton("Undo");
    private JButton redo_button = new JButton("Redo");
    protected UndoManager undo = new UndoManager(); /* manager for undo/redo support */

}
