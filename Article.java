import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public abstract class Article extends MaterialBlock{
    Vector<String> article;
    int height;
    Color backgroundColor;
    Color textColor;
    boolean bordered;

    Article(Vector<String> article) {
        super(DPGenGUI.style);
        this.article = article;
        for (int i=0; i<article.size(); i++)
            if (article.get(i).length() > 0 && article.get(i).charAt(0) == '.')
                article.set(i, article.get(i).substring(2));
        backgroundColor = DPGenGUI.backgroundColor;
        textColor = DPGenGUI.textColor;
    }

    public void refreshArticleLines() {
        LineCompacter compacter = new LineCompacter(DPGenGUI.lineSize);
        Vector<String> newArticle = new Vector<String>();
        for (String line : article) {
            compacter.setLongLine(line);
            compacter.compact();
            newArticle.addAll(compacter.getLines());
        }
        article = newArticle;
    }

    public void trimArticle() {
        String text = DPGenGUI.getStringFromStringVector(article, false);
        LineCompacter compacter = new LineCompacter(DPGenGUI.lineSize);
        compacter.setLongLine(text);
        compacter.compact();
        article = compacter.getLines();
    }

    int maximumLineWidthInPixels() {
        BufferedImage image = new BufferedImage(DPGenGUI.width + 10, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        FontMetrics metrics = graphics2D.getFontMetrics();
        int max = 0;
        for (String line : article) {
            int currWidth = metrics.stringWidth(line);
            if (currWidth > max) max = currWidth;
        }
        return max;
    }

    void setBordered(boolean bordered) {
        this.bordered = bordered;
    }

    void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
}
