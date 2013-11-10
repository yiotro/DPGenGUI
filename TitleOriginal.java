import java.awt.*;

public class TitleOriginal extends Title {

    TitleOriginal(String header) {
        super(header);
        style = ElementFactory.STYLE_ORIGINAL;
    }

    @Override
    int getHeight() {
        return height;
    }

    @Override
    public int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        graphics2D.setColor(DPGenGUI.HEADER_COLOR);
        graphics2D.setStroke(new BasicStroke(1));
        int x1, x2, y1, y2;
        int bound_left, bound_right, bound_up, bound_down;
        int t;
        bound_left = 15;
        bound_right = DPGenGUI.width - 65;
        bound_up = hPos + 5;
        bound_down = hPos - 35;
        int dx = bound_right - bound_left;
        int dy = bound_up - bound_down;
        for (t = 0; t < dx + dy; t += 13) {
            x1 = Math.max(bound_left, bound_left - dy + t);
            y1 = Math.min(bound_up, bound_up - dy + t);
            x2 = Math.min(bound_right, bound_right - dx + t);
            y2 = Math.max(bound_down, bound_down - dx + t);
            graphics2D.drawLine(x1, y1, x2, y2);
        }
        graphics2D.setFont(DPGenGUI.headerFont);
        graphics2D.setColor(textColor);
        graphics2D.drawString(header, getAlignedTextPosition(header, bound_left + 10, bound_right - 10, DPGenGUI.headerFont), hPos - 7);
        height = 45;
        return hPos + height;
    }

    @Override
    Rectangle getSelectionRegion() {
        return new Rectangle(10, hPos - 40, DPGenGUI.width - 55, 50);
    }
}
