import java.awt.*;
import java.util.Vector;

public abstract class PictureBlock extends MaterialBlock {
    Image picture;
    String description;
    int pictureHeight;

    protected PictureBlock(Image picture, String description) {
        super(DPGenGUI.style);
        this.picture = picture;
        this.description = description;
        blockWidth = picture.getWidth(null);
    }
}
