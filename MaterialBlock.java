import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class MaterialBlock {
    abstract int show(Graphics2D graphics2D, int hPos);
    int blockWidth;
    int hPos;
    abstract Rectangle getSelectionRegion();
    abstract int getHeight();
    int style;

    protected MaterialBlock(int style) {
        this.style = style;
    }

    public void refreshHeight() {
        BufferedImage temp = new BufferedImage(DPGenGUI.width, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) temp.getGraphics();
        show(graphics2D, hPos);
    }
}
