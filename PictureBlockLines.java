import java.awt.*;
import java.util.Random;
import java.util.Vector;

class PictureBlockLines extends PictureBlock {
    private final int commentX;
    private final int commentY;
    private final int commentHeight;
    private final Vector<String> lines;

    PictureBlockLines(Image picture, String description) {
        super(picture, description);
        int resizedImageWidth = DPGenGUI.width - 100;
        this.picture = DPGenGUI.getResizedImageByWidth(picture, resizedImageWidth);
        this.description = description;
        LineCompacter compacter = new LineCompacter(DPGenGUI.howManySymbolsFitInWidth((int) (blockWidth / 2), DPGenGUI.commentFont) - 2);
        compacter.setLongLine(description);
        compacter.compact();
        lines = compacter.getLines();
        commentHeight = 10 + 20 * lines.size();
        pictureHeight = picture.getHeight(null);
        style = ElementFactory.STYLE_LINES;

        commentX = 25;
        commentY = 5;
    }

    @Override
    int getHeight() {
        return picture.getHeight(null) + 20;
    }

    @Override
    public int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        blockWidth = picture.getWidth(null);
        hPos += 10;
        Random random = new Random();
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(3));
        int leftCorner = DPGenGUI.width / 2 - blockWidth / 2 - 1;
        int lowerCorner = hPos - 30 - 1;
        int rightCorner = leftCorner + picture.getWidth(null) + 1;
        int upperCorner = lowerCorner + picture.getHeight(null) + 1;
        int lineLength = picture.getWidth(null) / 8;
        graphics2D.drawLine(leftCorner, lowerCorner, leftCorner + lineLength, lowerCorner);
        graphics2D.drawLine(leftCorner, lowerCorner, leftCorner, lowerCorner + lineLength);
        graphics2D.drawLine(rightCorner, lowerCorner, rightCorner - lineLength, lowerCorner);
        graphics2D.drawLine(rightCorner, lowerCorner, rightCorner, lowerCorner + lineLength);
        graphics2D.drawLine(leftCorner, upperCorner, leftCorner + lineLength, upperCorner);
        graphics2D.drawLine(leftCorner, upperCorner, leftCorner, upperCorner - lineLength);
        graphics2D.drawLine(rightCorner, upperCorner, rightCorner - lineLength, upperCorner);
        graphics2D.drawLine(rightCorner, upperCorner, rightCorner, upperCorner - lineLength);
        graphics2D.drawImage(picture, DPGenGUI.width / 2 - blockWidth / 2, hPos - 30, null);
        if (lines.size() > 0) {
            graphics2D.setFont(DPGenGUI.commentFont);
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(DPGenGUI.width / 2 - blockWidth / 2 + commentX, hPos - 30 + commentY, blockWidth / 2, commentHeight);
            graphics2D.setColor(DPGenGUI.pictureBoundColor);
            graphics2D.setStroke(new BasicStroke(4));
            graphics2D.drawLine(DPGenGUI.width / 2 - blockWidth / 2 + commentX, hPos - 28 + commentY, DPGenGUI.width / 2 - blockWidth / 2 + commentX, hPos - 32 + commentHeight + commentY);
            graphics2D.setColor(DPGenGUI.textColor);
            for (int i=0; i<lines.size(); i++) {
                graphics2D.drawString(lines.get(i), DPGenGUI.width / 2 - blockWidth / 2 + commentX + 10, hPos - 11 + commentY + 20 * i);
            }
        }
        hPos -= 5;
        return hPos + picture.getHeight(null) + 20;
    }

    @Override
    Rectangle getSelectionRegion() {
        return new Rectangle(DPGenGUI.width / 2 - blockWidth / 2 - 10, hPos - 30 - 10, picture.getWidth(null) + 20, picture.getHeight(null) + 20);
    }
}
