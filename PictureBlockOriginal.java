import java.awt.*;
import java.util.Random;
import java.util.Vector;

class PictureBlockOriginal extends PictureBlock {
    private final int commentX;
    private final int commentY;
    private final int commentHeight;
    private final Vector<String> lines;

    PictureBlockOriginal(Image picture, String description) {
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
        style = ElementFactory.STYLE_ORIGINAL;

        Random random = new Random();
        if (random.nextInt(4) < 3) {
            commentX = 25;
            commentY = 5;
        } else {
            commentX = blockWidth / 2 - 5;
            commentY = 5;
        }
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
        graphics2D.drawRect(DPGenGUI.width / 2 - blockWidth / 2 - 1, hPos - 30 - 1, picture.getWidth(null) + 1, picture.getHeight(null) + 1);
        graphics2D.drawImage(picture, DPGenGUI.width / 2 - blockWidth / 2, hPos - 30, null);
        if (random.nextInt(7) < 6) {
            graphics2D.setColor(DPGenGUI.backgroundColor);
            graphics2D.setStroke(new BasicStroke(10));
            graphics2D.drawLine(DPGenGUI.width / 2 - blockWidth / 2 - 10, hPos - 30 + 10, DPGenGUI.width / 2 - blockWidth / 2 + 10, hPos - 30 - 10);
            graphics2D.drawLine(DPGenGUI.width / 2 - blockWidth / 2 - 10, hPos - 30 + 20, DPGenGUI.width / 2 - blockWidth / 2 + 20, hPos - 30 - 10);
            graphics2D.setStroke(new BasicStroke(2.5f));
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawLine(DPGenGUI.width / 2 - blockWidth / 2, hPos - 30 + 20 - 1, DPGenGUI.width / 2 - blockWidth / 2 + 20 - 1, hPos - 30);
        }
        if (random.nextInt(6) < 5 && commentX < blockWidth / 4) {
            graphics2D.setStroke(new BasicStroke(8));
            graphics2D.setColor(DPGenGUI.thematicColor);
            graphics2D.drawLine(DPGenGUI.width / 2 + blockWidth / 2 - 5, hPos - 25, DPGenGUI.width / 2 + blockWidth / 2 - 25, hPos - 25);
            graphics2D.drawLine(DPGenGUI.width / 2 + blockWidth / 2 - 5, hPos - 25, DPGenGUI.width / 2 + blockWidth / 2 - 5, hPos - 5);
        }
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
