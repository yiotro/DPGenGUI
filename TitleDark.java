import java.awt.*;

public class TitleDark extends Title{

    public TitleDark(String header) {
        super(header);
        style = ElementFactory.STYLE_DARK;

    }

    @Override
    int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        graphics2D.setFont(DPGenGUI.headerFont);
        graphics2D.setColor(textColor);
        graphics2D.drawString(header, 25, hPos - 7);
        graphics2D.setColor(DPGenGUI.thematicColor);
        graphics2D.setStroke(new BasicStroke(3));
        FontMetrics metrics = graphics2D.getFontMetrics();
        graphics2D.drawLine(30, hPos - 7 + 3, 25 + metrics.stringWidth(header), hPos - 7 + 3);
        height = 45;
        return hPos + height;
    }

    @Override
    Rectangle getSelectionRegion() {
        return new Rectangle(10, hPos - 40, DPGenGUI.width - 55, 50);
    }

    @Override
    int getHeight() {
        return height;
    }
}
