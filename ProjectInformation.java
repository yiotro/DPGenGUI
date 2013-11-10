import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

class ProjectInformation implements Serializable {
    final Vector<PIBlock> blocks;
    final String projectName;
    final Color backgroundColor;
    final Color textColor;
    final Color commonArticleColor;
    final Color nameColor;
    final Color thematicColor;
    final Font textFont;
    final int style;
    final Vector<String> sign;

    ProjectInformation(Vector<PIBlock> blocks,
                       String projectName,
                       Color backgroundColor,
                       Color textColor,
                       Color commonArticleColor,
                       Color nameColor,
                       Color thematicColor,
                       Font textFont,
                       int style,
                       Vector<String> sign) {
        this.blocks = blocks;
        this.projectName = projectName;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.commonArticleColor = commonArticleColor;
        this.nameColor = nameColor;
        this.thematicColor = thematicColor;
        this.textFont = textFont;
        this.style = style;
        this.sign = sign;
    }

    Vector<PIBlock> getBlocks() {
        return blocks;
    }

    String getProjectName() {
        return projectName;
    }

    Color getBackgroundColor() {
        return backgroundColor;
    }
}
