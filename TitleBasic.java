import java.awt.*;

public class TitleBasic extends Title{

    public TitleBasic(String header) {
        super(header);
        style = ElementFactory.STYLE_BASIC;
    }

    @Override
    int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        graphics2D.setFont(DPGenGUI.headerFont);
        graphics2D.setColor(textColor);
        graphics2D.drawString(header, 25, hPos - 7);
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
