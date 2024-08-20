package Model;
import javax.swing.JButton;
import java.awt.*;

public class CustomButton extends JButton {

    public CustomButton(String text, int x, int y, int width, int height) {
        super(text);
        setBounds(x, y, width, height);
        setBackground(Color.black);
        setForeground(Color.WHITE);
        setFocusable(false);
        setFont(new Font("Arial", Font.PLAIN, 18));
    }
}
