import java.awt.*;
import javax.swing.*;

public class Window extends  JPanel{
    public Window() {
        int width = 31;
        int height = 13;
        setPreferredSize(new Dimension(width * 16 * 3, height * 16 * 3));
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Bomberman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Window());
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
