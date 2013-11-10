import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

class ProjectInformation implements Serializable {
    Vector<PIBlock> blocks;
    String projectName;
    Color backgroundColor;
    Color textColor;
    Color commonArticleColor;
    Color nameColor;
    Color thematicColor;
    Font textFont;
    int style;
    Vector<String> sign;
    Font font2, font3, font4, font5;
    int integer1, integer2, integer3, integer4, integer5;
    Color color1, color2, color3;
    String str1, str2, str3, str4;

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

    void setFont2(Font font2) {
        this.font2 = font2;
    }

    void setFont3(Font font3) {
        this.font3 = font3;
    }

    void setFont4(Font font4) {
        this.font4 = font4;
    }

    void setFont5(Font font5) {
        this.font5 = font5;
    }

    void setInteger1(int integer1) {
        this.integer1 = integer1;
    }

    void setInteger2(int integer2) {
        this.integer2 = integer2;
    }

    void setInteger3(int integer3) {
        this.integer3 = integer3;
    }

    void setInteger4(int integer4) {
        this.integer4 = integer4;
    }

    void setInteger5(int integer5) {
        this.integer5 = integer5;
    }

    void setColor1(Color color1) {
        this.color1 = color1;
    }

    void setColor2(Color color2) {
        this.color2 = color2;
    }

    void setColor3(Color color3) {
        this.color3 = color3;
    }

    void setStr1(String str1) {
        this.str1 = str1;
    }

    void setStr2(String str2) {
        this.str2 = str2;
    }

    void setStr3(String str3) {
        this.str3 = str3;
    }

    void setStr4(String str4) {
        this.str4 = str4;
    }
}
