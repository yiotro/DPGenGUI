import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class MaterialBlock {
    abstract int show(Graphics2D graphics2D, int hPos);
    int blockWidth;
    int hPos;
    abstract Rectangle getSelectionRegion();
    abstract int getHeight();
    int style;
    int textAlign;
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_RIGHT = 2;

    MaterialBlock(int style) {
        this.style = style;
        textAlign = ALIGN_LEFT;
    }

    public void refreshHeight() {
        BufferedImage temp = new BufferedImage(DPGenGUI.width, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) temp.getGraphics();
        show(graphics2D, hPos);
    }

    int getAlignedTextPosition(String textLine, int leftLimit, int rightLimit, Font font) {
        BufferedImage temp = new BufferedImage(DPGenGUI.width, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) temp.getGraphics();
        graphics2D.setFont(font);
        FontMetrics metrics = graphics2D.getFontMetrics();
        int textWidth = metrics.stringWidth(textLine);
        switch (textAlign) {
            case ALIGN_LEFT: return leftLimit + 5;
            default: return leftLimit + 5;
            case ALIGN_CENTER: return leftLimit + (int) ((rightLimit - leftLimit - textWidth) / 2.0f);
            case ALIGN_RIGHT: return rightLimit - 5 - textWidth;
        }
    }

    public void setTextAlign(int textAlign) {
        this.textAlign = textAlign;
    }
}
