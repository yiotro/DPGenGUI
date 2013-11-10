import java.awt.*;
import java.util.Vector;

public class ArticleOriginal extends Article{

    ArticleOriginal(Vector<String> article) {
        super(article);
        bordered = false;
        style = ElementFactory.STYLE_ORIGINAL;
    }

    @Override
    int getHeight() {
        return height + 20;
    }

    @Override
    public int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(10, hPos - 20, DPGenGUI.width - 20, article.size() * 24);
        if (bordered) {
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(10, hPos - 20, DPGenGUI.width - 20, article.size() * 24);
        }
        graphics2D.setColor(DPGenGUI.pictureBoundColor);
        graphics2D.setFont(DPGenGUI.textFont);
        graphics2D.setStroke(new BasicStroke(3));
        hPos -= 20;
        graphics2D.drawLine(10, hPos, 10, hPos + 20);
        graphics2D.drawLine(10, hPos, 30, hPos);
        hPos += 20;
        graphics2D.setColor(textColor);
        for (String line : article) {
            graphics2D.drawString(line, 15, hPos);
            hPos += 24;
        }
        hPos += 25;
        height = hPos - this.hPos - 25;
        if (height < 20) height += 24;
        return hPos;
    }

    @Override
    Rectangle getSelectionRegion() {
        return new Rectangle(5, hPos - 25, DPGenGUI.width - 20, height + 10);
    }
}
