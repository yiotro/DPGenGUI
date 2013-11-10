import java.awt.*;

class TitleDark extends Title{

    public TitleDark(String header) {
        super(header);
        style = ElementFactory.STYLE_DARK;

    }

    @Override
    int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        graphics2D.setFont(DPGenGUI.headerFont);
        graphics2D.setColor(textColor);
        int alignedPos = getAlignedTextPosition(header, 25, DPGenGUI.width - 50, DPGenGUI.headerFont);
        graphics2D.drawString(header, alignedPos, hPos - 7);
        graphics2D.setColor(DPGenGUI.thematicColor);
        graphics2D.setStroke(new BasicStroke(3));
        FontMetrics metrics = graphics2D.getFontMetrics();
        graphics2D.drawLine(alignedPos + 5, hPos - 7 + 3, alignedPos + metrics.stringWidth(header), hPos - 7 + 3);
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
