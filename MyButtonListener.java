import sun.plugin.com.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

class MyButtonListener implements ActionListener {

    private final DPGenGUI genGUI;

    MyButtonListener(DPGenGUI frame) {
        this.genGUI = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Настройки поста")) {
            genGUI.mainCardLayout.show(genGUI.mainCardPanel, "options");
            genGUI.nameField.setText(genGUI.name);
            genGUI.nameField.requestFocus();
        } else if (command.equals("Создать абзац")) {
            Article article = genGUI.elementFactory.createArticle(new Vector<String>());
            genGUI.addElement(article);
            genGUI.elementList.setSelectedIndex(genGUI.materialBlocks.size() - 1);
        } else if (command.equals("Создать заголовок")) {
            Title header = genGUI.elementFactory.createTitle(" ");
            genGUI.addElement(header);
            genGUI.elementList.setSelectedIndex(genGUI.materialBlocks.size() - 1);
        } else if (command.equals("Создать картинку")) {
            BufferedImage bufferedImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = bufferedImage.getGraphics();
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(0, 0, 300, 300);
            PictureBlock pictureBlock = genGUI.elementFactory.createPictureBlock(bufferedImage, "");
            genGUI.addElement(pictureBlock);
            genGUI.elementList.setSelectedIndex(genGUI.materialBlocks.size() - 1);
            genGUI.imageTextField.setText("Комментарий к картинке");
            genGUI.imageTextField.selectAll();
        } else if (command.equals("Применить абзац")) {
            if (genGUI.selectedBlock == null) return;
            if (!(genGUI.selectedBlock instanceof Article)) return;
            StringTokenizer textTokenizer = new StringTokenizer(genGUI.articleArea.getText(), "\n");
            LineCompacter compacter = new LineCompacter(DPGenGUI.lineSize);
            Vector<String> miniTexts = new Vector<String>();
            String token;
            while (textTokenizer.hasMoreTokens()) {
                token = textTokenizer.nextToken();
                compacter.setLongLine(token);
                compacter.compact();
                miniTexts.addAll(compacter.getLines());
            }
            ((Article) genGUI.selectedBlock).article = miniTexts;
            genGUI.selectedBlock.refreshHeight();
            genGUI.compose();
        } else if (command.equals("Применить имя")) {
            String name = genGUI.nameField.getText();
            if (name.length() > DPGenGUI.POST_NAME_MAX_SIZE) name = name.substring(0, DPGenGUI.POST_NAME_MAX_SIZE);
            genGUI.name = name;
            genGUI.compose(false);
        } else if (command.equals("Применить заголовок")) {
            if (genGUI.selectedBlock == null) return;
            if (!(genGUI.selectedBlock instanceof Title)) return;
            String text = genGUI.titleField.getText();
            if (text.length() > DPGenGUI.TITLE_MAX_SIZE) text = text.substring(0, DPGenGUI.TITLE_MAX_SIZE);
            ((Title) genGUI.selectedBlock).header = text;
            genGUI.selectedBlock.refreshHeight();
            genGUI.compose();
        } else if (command.equals("Применить картинку")) {
            if (genGUI.selectedBlock == null) return;
            if (!(genGUI.selectedBlock instanceof PictureBlock)) return;
            String text = genGUI.imageTextField.getText();
            PictureBlock pictureBlock = genGUI.elementFactory.createPictureBlock(genGUI.currentImage, text, genGUI.selectedBlock.style);
            pictureBlock.refreshHeight();
            genGUI.materialBlocks.setElementAt(pictureBlock, genGUI.selectedIndex);
            genGUI.refreshLineSize();
            genGUI.compose();
        } else if (command.equals("Вставить")) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            try {
                BufferedImage image = (BufferedImage) clipboard.getData(DataFlavor.imageFlavor);
                genGUI.currentImage = DPGenGUI.getCopyOfImage(image);
                image = (BufferedImage) DPGenGUI.getResizedImageByWidth(image, 300);
                ImageIcon icon = new ImageIcon(image);
                genGUI.imageOnEditLabel.setIcon(icon);
            } catch (Exception ignored) {}
        } else if (command.equals("Quit")) {
            genGUI.askIfUserWantsToCloseProgram();
        } else if (command.equals("Сохранить")) {
            genGUI.savePost();
        } else if (command.equals("Загрузить")) {
            genGUI.loadPost();
        } else if (command.equals("Обновить пост")) {
            genGUI.compose();
        } else if (command.equals("Новый пост")) {
            genGUI.clearPost(true);
        } else if (command.equals("Удалить")) {
            genGUI.deleteSelectedBlock();
        } else if (command.equals("Выше")) {
            genGUI.moveUpSelectedBlock();
        } else if (command.equals("Ниже")) {
            genGUI.moveDownSelectedBlock();
        } else if (command.equals("Клонировать")) {
            genGUI.cloneSelectedBlock();
        } else if (command.equals("Экспорт в PNG")) {
            genGUI.exportToPNG();
        } else if (command.equals("apply post background color")) {
            Color color = JColorChooser.showDialog(genGUI, "Выбрать фон поста", DPGenGUI.backgroundColor);
            if (color != null) DPGenGUI.setBackgroundColor(color);
            genGUI.compose(false);
        } else if (command.equals("apply post text color")) {
            Color color = JColorChooser.showDialog(genGUI, "Выбрать цвет текста", DPGenGUI.textColor);
            if (color != null) {
                DPGenGUI.setTextColor(color);
                genGUI.refreshTextColorOfArticles();
                genGUI.compose(false);
            }
        } else if (command.equals("change article background color") || command.equals("Задать цвет фона")) {
            if (genGUI.selectedBlock instanceof Article) {
                Color color = JColorChooser.showDialog(genGUI, "Выбрать цвет фона абзаца", ((Article) genGUI.selectedBlock).backgroundColor);
                if (color != null) ((Article) genGUI.selectedBlock).setBackgroundColor(color);
                genGUI.compose(false);
            }
        } else if (command.equals("change article text color") || command.equals("Задать цвет текста")) {
            if (genGUI.selectedBlock instanceof Article) {
                Color color = JColorChooser.showDialog(genGUI, "Выбрать цвет текста абзаца", ((Article) genGUI.selectedBlock).textColor);
                if (color != null) ((Article) genGUI.selectedBlock).setTextColor(color);
                genGUI.compose(false);
            } else if (genGUI.selectedBlock instanceof Title) {
                Color color = JColorChooser.showDialog(genGUI, "Выбрать цвет текста заголовка", ((Title) genGUI.selectedBlock).textColor);
                if (color != null) ((Title) genGUI.selectedBlock).setTextColor(color);
                genGUI.compose(false);
            }
        } else if (command.equals("set common article color")) {
            Color color = JColorChooser.showDialog(genGUI, "Задать общий цвет абзацев", genGUI.commonArticleBackgroundColor);
            if (color != null) genGUI.setCommonArticleBackgroundColor(color);
        } else if (command.equals("Цвет названия")) {
            Color color = JColorChooser.showDialog(genGUI, "Выбрать цвет названия поста", genGUI.nameColor);
            if (color != null) genGUI.nameColor = color;
            genGUI.compose(false);
        } else if (command.equals("Цвет заголовка")) {
            if (genGUI.selectedBlock instanceof Title) {
                Color color = JColorChooser.showDialog(genGUI, "Цвет заголовка", ((Title) genGUI.selectedBlock).textColor);
                if (color != null) ((Title) genGUI.selectedBlock).textColor = color;
                genGUI.compose(false);
            }
        } else if (command.equals("apply signature")) {
            String text = genGUI.signatureField.getText();
            LineCompacter compacter = new LineCompacter(DPGenGUI.lineSize - 10);
            compacter.setLongLine(text);
            compacter.compact();
            genGUI.signature = compacter.getLines();
            genGUI.compose(false);
        } else if (command.equals("Цвет подписи")) {
            Color color = JColorChooser.showDialog(genGUI, "Цвет подписи", genGUI.signatureColor);
            if (color != null) genGUI.signatureColor = color;
            genGUI.compose(false);
        } else if (command.equals("Размер текста")) {
            try {
                int size = Integer.valueOf(genGUI.textSizeField.getText());
                DPGenGUI.textFont = DPGenGUI.textFont.deriveFont((float) size);
                genGUI.refreshLineSize();
                genGUI.compose(false);
            } catch (Exception ignored) {}
        } else if (command.equals("Задать тематический цвет поста O_o")) {
            Color color = JColorChooser.showDialog(genGUI, "Тематический цвет поста", DPGenGUI.thematicColor);
            if (color != null) genGUI.setThematicColor(color);
            genGUI.compose(false);
        } else if (command.equals("Помощь, епта")) {
            StringBuilder help = new StringBuilder();
            help.append("Привет, это прога для создания длиннопостов на пикабу.\n");
            help.append("Сделал ее я, yiotro.\n");
            help.append("В принципе, прога довольно простая, кнопок хоть и много,\n");
            help.append("но по ним легко понять, что они делают.\n");
            help.append("Надо заметить, что на элементы поста можно кликать правой\n");
            help.append("кнопкой мыши. И если текст вдруг вылез за пределы абзаца,\n");
            help.append("то нажмите 'Схлопнуть абзац'\n");
            help.append("Хочу сказать спасибо пользователю Hexen за помощь\n");
            help.append("Особая благодарность моему другу faragilus за помощь :)\n");
            help.append("Мой e-mail: yiotro@hotmail.com");
            JOptionPane.showMessageDialog(genGUI, help.toString(), "Помощь", JOptionPane.INFORMATION_MESSAGE);
        } else if (command.equals("Добавить картинку из папки")) {
            genGUI.loadPictureFromFile();
        } else if (command.equals("По часовой")) {
            if (genGUI.selectedBlock instanceof PictureBlock) {
                ((PictureBlock) genGUI.selectedBlock).picture = DPGenGUI.rotateImage(((PictureBlock) genGUI.selectedBlock).picture, 90);
                genGUI.selectedBlock.refreshHeight();
                genGUI.compose();
            }
        } else if (command.equals("Против часовой")) {
            if (genGUI.selectedBlock instanceof PictureBlock) {
                ((PictureBlock) genGUI.selectedBlock).picture = DPGenGUI.rotateImage(((PictureBlock) genGUI.selectedBlock).picture, 270);
                genGUI.selectedBlock.refreshHeight();
                genGUI.compose();
            }
        } else if (command.equals("Вверх ногами")) {
            if (genGUI.selectedBlock instanceof PictureBlock) {
                ((PictureBlock) genGUI.selectedBlock).picture = DPGenGUI.rotateImage(((PictureBlock) genGUI.selectedBlock).picture, 180);
                genGUI.selectedBlock.refreshHeight();
                genGUI.compose();
            }
        } else if (command.equals("trim article")) {
            if (genGUI.selectedBlock instanceof Article) {
                ((Article) genGUI.selectedBlock).trimArticle();
                genGUI.compose();
            }
        } else if (command.equals("align text left")) {
            genGUI.selectedBlock.setTextAlign(MaterialBlock.ALIGN_LEFT);
            genGUI.compose();
        } else if (command.equals("align text center")) {
            genGUI.selectedBlock.setTextAlign(MaterialBlock.ALIGN_CENTER);
            genGUI.compose();
        } else if (command.equals("align text right")) {
            genGUI.selectedBlock.setTextAlign(MaterialBlock.ALIGN_RIGHT);
            genGUI.compose();
        } else if (command.equals("choose font")) {
            Font[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
            Object[] fontNames = new Object[allFonts.length];
            int i = 0;
            for (Font f : allFonts) {
                fontNames[i] = f.getName();
                i++;
            }
            String s = (String) JOptionPane.showInputDialog(genGUI, "Выберите шрифт", "Выбор шрифта из списка", JOptionPane.PLAIN_MESSAGE, null, fontNames, DPGenGUI.textFont.getName());
            if ((s != null) && (s.length() > 0)) {
                genGUI.setLoadedFont(new Font(s, Font.PLAIN, 16));
            }
            genGUI.compose(false);
        } else if (command.equals("load font")) {
            genGUI.loadFontFromFile();
            genGUI.compose(false);
        } else if (command.equals("switch border")) {
            if (genGUI.selectedBlock instanceof Article) {
                ((Article) genGUI.selectedBlock).bordered = !((Article) genGUI.selectedBlock).bordered;
                genGUI.compose();
            }
        } else if (command.equals("Отмена")) {
            genGUI.undoAction();
        }
    }
}
