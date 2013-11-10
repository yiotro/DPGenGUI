import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

class PIBlock implements Serializable {
    Vector<String> text;
    ImageIcon imageIcon;
    int type;
    Color textColor;
    Color backColor;
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_ARTICLE = 1;
    public static final int TYPE_PICTURE = 2;
    boolean bordered;
    int style;
}
