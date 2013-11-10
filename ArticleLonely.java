import java.awt.*;
import java.util.Vector;

public class ArticleLonely extends Article{

    public ArticleLonely(Vector<String> article) {
        super(article);
        bordered = false;
        style = ElementFactory.STYLE_LONELY_LUCKILY;
    }

    @Override
    int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        graphics2D.setColor(backgroundColor);
        int fontHeight = 22;
        graphics2D.fillRect(10, hPos - 20, DPGenGUI.width - 20, article.size() * fontHeight + 5);
        if (bordered) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(10, hPos - 20, DPGenGUI.width - 20, article.size() * fontHeight + 5);
        }
        graphics2D.setFont(DPGenGUI.textFont);
        graphics2D.setColor(textColor);
        for (String line : article) {
            graphics2D.drawString(line, 15, hPos);
            hPos += fontHeight;
        }
        hPos += fontHeight + 1;
        height = hPos - this.hPos - fontHeight - 1;
        if (height < 20) height += fontHeight;
        return hPos;
    }

    @Override
    Rectangle getSelectionRegion() {
        return new Rectangle(5, hPos - 25, DPGenGUI.width - 20, height + 10);
    }

    @Override
    int getHeight() {
        return height + 20;
    }
}
