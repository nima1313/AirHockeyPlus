package Model;

import javax.swing.*;

public class CustomCheckBox extends JCheckBox {
    CustomCheckBox(String text, int x, int y, int width, int height){
        super(text);
        setFocusable(false);
        setBounds(x,y,width,height);
    }
}
