import java.awt.*;
import java.util.Vector;

public abstract class Article extends MaterialBlock{
    Vector<String> article;
    int height;
    Color backgroundColor;
    Color textColor;
    boolean bordered;

    protected Article(Vector<String> article) {
        super(DPGenGUI.style);
        this.article = article;
        for (int i=0; i<article.size(); i++)
            if (article.get(i).charAt(0) == '.')
                article.set(i, article.get(i).substring(2));
        backgroundColor = DPGenGUI.backgroundColor;
        textColor = DPGenGUI.textColor;
    }

    public void refreshArticleLines() {
        String text = DPGenGUI.getStringFromStringVector(article, false);
        LineCompacter compacter = new LineCompacter(DPGenGUI.lineSize);
        compacter.setLongLine(text);
        compacter.compact();
        article = compacter.getLines();
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
