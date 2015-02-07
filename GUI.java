import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;

public class GUI extends JFrame {
    private JLabel info = new JLabel("Text will be displayed here");
    private JTextField text_box = new JTextField(10);
    private JLabel caret_info = new JLabel("Caret info will be displayed here");
    public GUI() {
        setTitle("Textbox");
        setSize(300, 200);
        setLocationRelativeTo(null); /* centers window on screen */
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //info.setFont(new Font("Georgia", Font.PLAIN, 14));
        JPanel pnl = (JPanel) getContentPane();
        //pnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnl.add(text_box, BorderLayout.NORTH);
        pnl.add(info, BorderLayout.CENTER);
        pnl.add(caret_info, BorderLayout.SOUTH);
        pack();
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
    }
    public void setInfo(String s) {info.setText(s);}
    public void setCaretInfo(String s) {caret_info.setText(s);}
    public String getText() {return text_box.getText();}
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();

                gui.setVisible(true);
            }
        });
    }

}
