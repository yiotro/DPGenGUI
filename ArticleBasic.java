import java.awt.*;
import java.util.Vector;

class ArticleBasic extends Article{

    public ArticleBasic(Vector<String> article) {
        super(article);
        bordered = false;
        style = ElementFactory.STYLE_BASIC;
    }

    @Override
    int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        graphics2D.setColor(backgroundColor);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.fillRect(10, hPos - 20, DPGenGUI.width - 20, article.size() * 24 + 5);
        if (bordered) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(10, hPos - 20, DPGenGUI.width - 20, article.size() * 24 + 5);
        }
        graphics2D.setFont(DPGenGUI.textFont);
        graphics2D.setColor(textColor);
        for (String line : article) {
            graphics2D.drawString(line, getAlignedTextPosition(line, 10, DPGenGUI.width - 10, DPGenGUI.textFont), hPos);
            hPos += 24;
        }
        hPos += 25;
        height = hPos - this.hPos - 25 + 4;
        if (height < 20) height += 24;
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
