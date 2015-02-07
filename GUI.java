import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JLabel info = new JLabel("Text will be displayed here");
    private JTextField text_box = new JTextField(10);
    public GUI() {
        setTitle("Textbox");
        setSize(300, 200);
        setLocationRelativeTo(null); /* centers window on screen */
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        info.setFont(new Font("Georgia", Font.PLAIN, 14));

        JPanel pnl = (JPanel) getContentPane();
        pnl.add(info, BorderLayout.CENTER);
        //pnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnl.add(text_box, BorderLayout.NORTH);
        pack();
    }
    public void setInfo(String s) {info.setText(s);}
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
