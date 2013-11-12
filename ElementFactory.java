import java.awt.*;
import java.util.Vector;

class ElementFactory {
    private int style;
    public static final int STYLE_ORIGINAL = 0;
    public static final int STYLE_BASIC = 1;
    public static final int STYLE_LONELY_LUCKILY = 2;
    public static final int STYLE_DARK = 3;
    public static final int STYLE_PICTURES_ONLY = 4;
    public static final int STYLE_LINES = 5;
    public static final Color DEFAULT_DARK_STYLE_BACKGROUND_COLOR = new Color(100, 100, 100);

    public ElementFactory(int style) {
        this.style = style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public Article createArticle(Vector<String> article) {
        switch (style) {
            case STYLE_ORIGINAL: return new ArticleOriginal(article);
            case STYLE_BASIC: return new ArticleBasic(article);
            case STYLE_DARK: return new ArticleDark(article);
            case STYLE_LONELY_LUCKILY: return new ArticleLonely(article);
            case STYLE_PICTURES_ONLY: return new ArticleLonely(article);
            case STYLE_LINES: return new ArticleLines(article);
            default: return new ArticleOriginal(article);
        }
    }

    public Title createTitle(String title) {
        switch (style) {
            case STYLE_ORIGINAL: return new TitleOriginal(title);
            case STYLE_BASIC: return new TitleBasic(title);
            case STYLE_DARK: return new TitleDark(title);
            case STYLE_LONELY_LUCKILY: return new TitleLonely(title);
            case STYLE_PICTURES_ONLY: return new TitleBasic(title);
            case STYLE_LINES: return new TitleLines(title);
            default: return new TitleOriginal(title);
        }
    }

    public PictureBlock createPictureBlock(Image image, String description) {
        if (style != STYLE_LONELY_LUCKILY) {
            if (description.length() > DPGenGUI.PICTURE_COMMENT_MAX_SIZE) description = description.substring(0, DPGenGUI.PICTURE_COMMENT_MAX_SIZE);
        }
        switch (style) {
            case STYLE_ORIGINAL: return new PictureBlockOriginal(image, description);
            case STYLE_BASIC: return new PictureBlockBasic(image, description);
            case STYLE_DARK: return new PictureBlockDarkStyle(image, description);
            case STYLE_LONELY_LUCKILY: return new PictureBlockLonely(image, description);
            case STYLE_PICTURES_ONLY: return new PictureBlockPicturesOnly(image, description);
            case STYLE_LINES: return new PictureBlockLines(image, description);
            default: return new PictureBlockOriginal(image, description);
        }
    }

    public MaterialBlock copyOfElement(MaterialBlock element) {
        return copyOfElement(element, element.style);
    }

    public MaterialBlock copyOfElement(MaterialBlock element, int requestedStyle) {
        if (element == null) return createTitle("Не удалось скопировать");
        int styleOfFactory = style;
        style = requestedStyle;
        if (element instanceof Article) {
            Article copy = createArticle(((Article) element).article);
            copy.setBordered(((Article) element).bordered);
            copy.setTextColor(((Article) element).textColor);
            copy.setBackgroundColor(((Article) element).backgroundColor);
            copy.style = requestedStyle;
            copy.textAlign = element.textAlign;
            style = styleOfFactory;
            return copy;
        } else if (element instanceof Title) {
            Title copy = createTitle(((Title) element).header);
            copy.setTextColor(((Title) element).textColor);
            copy.style = requestedStyle;
            copy.textAlign = element.textAlign;
            style = styleOfFactory;
            return copy;
        } else if (element instanceof PictureBlock) {
            PictureBlock copy = createPictureBlock(((PictureBlock) element).picture, ((PictureBlock) element).description);
            copy.style = requestedStyle;
            style = styleOfFactory;
            copy.textAlign = element.textAlign;
            return copy;
        }
        // if fail
        return createTitle("Не удалось скопировать");
    }

    public PictureBlock createPictureBlock(Image picture, String description, int requestedStyle) {
        int styleOfFactory = style;
        style = requestedStyle;
        PictureBlock element = createPictureBlock(picture, description);
        style = styleOfFactory;
        return element;
    }

    public Title createTitle(String title, int requestedStyle) {
        int styleOfFactory = style;
        style = requestedStyle;
        Title element = createTitle(title);
        style = styleOfFactory;
        return element;
    }

    public Article createArticle(Vector<String> article, int requestedStyle) {
        int styleOfFactory = style;
        style = requestedStyle;
        Article element = createArticle(article);
        style = styleOfFactory;
        return element;
    }

    public Color getBackgroundColorOfStyle() {
        switch (style) {
            case STYLE_ORIGINAL: return DPGenGUI.DEFAULT_BACKGROUND_COLOR;
            case STYLE_BASIC: return DPGenGUI.DEFAULT_BACKGROUND_COLOR;
            case STYLE_DARK: return DEFAULT_DARK_STYLE_BACKGROUND_COLOR;
            case STYLE_LONELY_LUCKILY: return Color.WHITE;
            case STYLE_PICTURES_ONLY: return Color.WHITE;
            case STYLE_LINES: return DPGenGUI.DEFAULT_BACKGROUND_COLOR;
            default: return DPGenGUI.DEFAULT_BACKGROUND_COLOR;
        }
    }

    public Color getTextColorOfStyle() {
        switch (style) {
            case STYLE_ORIGINAL: return DPGenGUI.DEFAULT_TEXT_COLOR;
            case STYLE_BASIC: return DPGenGUI.DEFAULT_TEXT_COLOR;
            case STYLE_DARK: return new Color(230, 230, 230);
            case STYLE_LONELY_LUCKILY: return Color.BLACK;
            case STYLE_PICTURES_ONLY: return Color.BLACK;
            case STYLE_LINES: return DPGenGUI.textColor;
            default: return DPGenGUI.DEFAULT_TEXT_COLOR;
        }
    }

    public Color getArticleBackgroundOfStyle() {
        switch (style) {
            case STYLE_ORIGINAL: return DPGenGUI.DEFAULT_BACKGROUND_COLOR;
            case STYLE_BASIC: return DPGenGUI.DEFAULT_BACKGROUND_COLOR;
            case STYLE_DARK: return DEFAULT_DARK_STYLE_BACKGROUND_COLOR.brighter();
            case STYLE_LONELY_LUCKILY: return Color.WHITE;
            case STYLE_PICTURES_ONLY: return Color.WHITE;
            case STYLE_LINES: return new Color(230, 230, 230);
            default: return DPGenGUI.DEFAULT_BACKGROUND_COLOR;
        }
    }
}
