import java.awt.*;

public class PictureBlockBasic extends PictureBlock{


    public PictureBlockBasic(Image picture, String description) {
        super(picture, description);
        blockWidth = DPGenGUI.width - 40;
        int resizedImageWidth = blockWidth;
        this.picture = DPGenGUI.getResizedImageByWidth(picture, resizedImageWidth);
        this.description = description;
        pictureHeight = picture.getHeight(null);
        style = ElementFactory.STYLE_BASIC;
    }

    @Override
    int show(Graphics2D graphics2D, int hPos) {
        this.hPos = hPos;
        hPos += 5;
        graphics2D.drawImage(picture, DPGenGUI.width / 2 - blockWidth / 2, hPos - 30, null);
        graphics2D.setColor(DPGenGUI.textColor);
        graphics2D.setFont(DPGenGUI.commentFont);
        graphics2D.drawString(description, DPGenGUI.width / 2 - blockWidth / 2 + 5, hPos - 30 + 18 + picture.getHeight(null));
        return hPos + picture.getHeight(null) + 40;
    }

    @Override
    Rectangle getSelectionRegion() {
        return new Rectangle(DPGenGUI.width / 2 - blockWidth / 2 - 10, hPos - 30 - 10, picture.getWidth(null) + 20, picture.getHeight(null) + 40);
    }

    @Override
    int getHeight() {
        return picture.getHeight(null) + 40;
    }
}
