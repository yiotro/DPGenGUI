import java.awt.*;

public class TitleLines extends Title{

    public TitleLines(String header) {
        super(header);
        style = ElementFactory.STYLE_LINES;
    }

    @Override
    int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        graphics2D.setFont(DPGenGUI.headerFont);
        graphics2D.setColor(textColor);
        int alignedPos = getAlignedTextPosition(header, 25, DPGenGUI.width - 50, DPGenGUI.headerFont);
        graphics2D.drawString(header, alignedPos, hPos - 7);
        graphics2D.setColor(DPGenGUI.thematicColor);
        graphics2D.setStroke(new BasicStroke(2));
        FontMetrics metrics = graphics2D.getFontMetrics();
        int lineHeight = metrics.getHeight() / 3;
        graphics2D.drawLine(12, hPos - 7 + 3 - lineHeight, alignedPos, hPos - 7 + 3 - lineHeight);
        graphics2D.drawLine(alignedPos + metrics.stringWidth(header) + 5, hPos - 7 + 3 - lineHeight, DPGenGUI.width - 12, hPos - 7 + 3 - lineHeight);
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
