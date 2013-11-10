import java.awt.*;

public abstract class Title extends MaterialBlock {
    String header;
    int height;
    Color textColor;

    protected Title(String header) {
        super(DPGenGUI.style);
        this.header = header;
        textColor = DPGenGUI.textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
}
