import java.awt.*;
import java.util.Vector;

public class ArticleLines extends Article{

    public ArticleLines(Vector<String> article) {
        super(article);
        style = ElementFactory.STYLE_LINES;
    }

    @Override
    int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        graphics2D.setColor(backgroundColor);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.fillRect(10, hPos - 20, DPGenGUI.width - 20, article.size() * 24 + 2);
        if (bordered) {
            graphics2D.setStroke(new BasicStroke(4));
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(8, hPos - 22, DPGenGUI.width - 16, article.size() * 24 + 6);
        }
        graphics2D.setColor(DPGenGUI.pictureBoundColor);
        graphics2D.setStroke(new BasicStroke(2));
        int horizontalLineWidth = 21;
//        graphics2D.drawLine(9, hPos - 21, 9, hPos);
//        graphics2D.drawLine(9, hPos - 21, 9 + horizontalLineWidth, hPos - 21);
        int farCornerX = DPGenGUI.width - 9;
        int farCornerY = hPos - 17 + article.size() * 24;
        graphics2D.drawLine(farCornerX, farCornerY, farCornerX, farCornerY - 20);
        graphics2D.drawLine(farCornerX, farCornerY, farCornerX - horizontalLineWidth, farCornerY);
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
