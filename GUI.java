import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class GUI extends JFrame {
    private JLabel info = new JLabel("Text will be displayed here");
    private JTextField text_box = new JTextField(15);
    private JLabel caret_info = new JLabel("Caret info will be displayed here");
    private JLabel change_info = new JLabel("Change info will be displayed here");
    public GUI() {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout(FlowLayout.LEADING));
        frame.add(text_box);
        frame.add(info);
        frame.add(caret_info);
        frame.add(change_info);
        frame.setTitle("Textbox");
        frame.setSize(225, 150);
        frame.setComponentOrientation(
                ComponentOrientation.UNKNOWN);
        frame.setVisible(true);
        pack();
        frame.setLocationRelativeTo(null);
        text_box.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                int dot = e.getDot(); /* caret position where cursor started */
                int mark = e.getMark(); /* caret position where cursor ended */
                if(dot == mark) /* no selection */
                    setCaretInfo("Caret at " + dot + " (no selection)");
                else if(mark > dot) {
                    String selection = getText().substring(dot, mark);
                    setCaretInfo("Selected \"" + selection + "\"");
                }
                else {
                    String selection = getText().substring(mark, dot);
                    setCaretInfo("Selected \"" + selection + "\"");
                }
            }
        });
        text_box.getDocument().addDocumentListener(new MyDocumentListener());
    }
    public void setInfo(String s) {info.setText(s);}
    public void setCaretInfo(String s) {caret_info.setText(s);}
    public String getText() {return text_box.getText();}
    public static void main(String[] args) {
        GUI gui = new GUI();

        /*EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();

                gui.setVisible(true);
            }
        }); */
    }

}
