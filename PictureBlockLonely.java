import java.awt.*;
import java.util.Vector;

class PictureBlockLonely extends PictureBlock{

    private int wholeHeight;

    public PictureBlockLonely(Image picture, String description) {
        super(picture, description);
        style = ElementFactory.STYLE_LONELY_LUCKILY;
    }

    @Override
    int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        hPos += 5;
        Font font = new Font("Arial", Font.PLAIN, 16);
        int heightOfOneLine = font.getSize() + 4;
        int howManyLinesToBeUpper = 10;
        Image scaledPicture = DPGenGUI.getResizedImageByHeight(picture, howManyLinesToBeUpper * heightOfOneLine);
        LineCompacter compacter = new LineCompacter(4 + DPGenGUI.howManySymbolsFitInWidth(DPGenGUI.width - 30 - scaledPicture.getWidth(null), font));
        compacter.setLongLine(description);
        compacter.compact();
        Vector<String> wholeText = compacter.getLines();
        Vector<String> upperText, lowerText;
        if (wholeText.size() < howManyLinesToBeUpper) {
            upperText = wholeText;
            lowerText = new Vector<String>();
        } else {
            upperText = new Vector<String>();
            for (int i=0; i<howManyLinesToBeUpper; i++) upperText.add(wholeText.get(i));
            StringBuilder lowerBuffer = new StringBuilder();
            for (int i=howManyLinesToBeUpper; i<wholeText.size(); i++) lowerBuffer.append(wholeText.get(i));
            LineCompacter lowerCompacter = new LineCompacter(7 + DPGenGUI.howManySymbolsFitInWidth(DPGenGUI.width - 20, font));
            lowerCompacter.setLongLine(lowerBuffer.toString());
            lowerCompacter.compact();
            lowerText = lowerCompacter.getLines();
        }
        graphics2D.drawImage(scaledPicture, 10, hPos - 30, null);
        graphics2D.setFont(font);
        for (int i=0; i<upperText.size(); i++) graphics2D.drawString(upperText.get(i), 10 + scaledPicture.getWidth(null) + 10, hPos - 30 + 18 + i * heightOfOneLine);
        for (int i=0; i<lowerText.size(); i++) graphics2D.drawString(lowerText.get(i), 10, hPos - 30 + 18 + (i+howManyLinesToBeUpper) * heightOfOneLine);

        wholeHeight = 30 + Math.max((lowerText.size() + upperText.size()) * heightOfOneLine, scaledPicture.getHeight(null));
        return hPos + wholeHeight;
    }

    @Override
    Rectangle getSelectionRegion() {
        return new Rectangle(5, hPos - 35, DPGenGUI.width - 10, wholeHeight);
    }

    @Override
    int getHeight() {
        return wholeHeight;
    }
}
