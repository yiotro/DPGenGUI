import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

class PictureBlockDarkStyle extends PictureBlock{
    private static final int MINIMUM_PICTURE_WIDTH = 200;
    private final Vector<String> lines;

    PictureBlockDarkStyle() {
        this(new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB), " ");
    }

    @Override
    int getHeight() {
        return picture.getHeight(null) + 30;
    }

    PictureBlockDarkStyle(Image picture, String description) {
        super(picture, description);
        style = ElementFactory.STYLE_DARK;
        if (picture.getWidth(null) > DPGenGUI.width - 70 || picture.getWidth(null) < MINIMUM_PICTURE_WIDTH) {
            if (picture.getWidth(null) > DPGenGUI.width - 70) blockWidth = DPGenGUI.width - 60;
            else blockWidth = MINIMUM_PICTURE_WIDTH;
            float imageFraction = ((float) picture.getHeight(null)) / ((float) picture.getWidth(null));
            int resizedImageWidth = blockWidth - 10;
            int resizedImageHeight = (int) (((float) resizedImageWidth) * imageFraction);
            this.picture = DPGenGUI.getResizedImage(picture, resizedImageWidth, resizedImageHeight);
        } else {
            this.picture = picture;
            blockWidth = picture.getWidth(null) + 10;
        }
        this.description = description;
        LineCompacter compacter = new LineCompacter(DPGenGUI.howManySymbolsFitInWidth(this.picture.getWidth(null) - 20, DPGenGUI.commentFont));
        compacter.setLongLine(description);
        compacter.compact();
        lines = compacter.getLines();
    }

    @Override
    public int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        hPos -= 30;
        graphics2D.setColor(DPGenGUI.pictureBoundColor);
        graphics2D.fillRoundRect(DPGenGUI.width / 2 - blockWidth / 2 - 2, hPos - 2, blockWidth + 4, picture.getHeight(null) + 10 + 20 * lines.size() + 4, 15, 15);
        graphics2D.setColor(DPGenGUI.PICTURE_BLOCK_COLOR);
        graphics2D.fillRoundRect(DPGenGUI.width / 2 - blockWidth / 2, hPos, blockWidth, picture.getHeight(null) + 10 + 20 * lines.size(), 15, 15);
        graphics2D.drawImage(picture, DPGenGUI.width / 2 - blockWidth / 2 + 5, hPos + 5, null);
        graphics2D.setColor(Color.LIGHT_GRAY);
        graphics2D.setFont(DPGenGUI.commentFont);
        for (int i=0; i<lines.size(); i++) {
            graphics2D.drawString(lines.get(i), DPGenGUI.width / 2 - blockWidth / 2 + 5, hPos + picture.getHeight(null) + 23 + 20 * i);
        }
        return hPos + picture.getHeight(null) + 50 + 20 * lines.size();
    }

    @Override
    Rectangle getSelectionRegion() {
        return new Rectangle(DPGenGUI.width / 2 - blockWidth / 2 - 10, hPos - 30 - 10, picture.getWidth(null) + 20, picture.getHeight(null) + 23 + 20 * lines.size());
    }
}
