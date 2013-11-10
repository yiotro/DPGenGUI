import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
//import java.awt.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.Vector;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DPGenGUI extends JFrame implements ClipboardOwner, ActionListener {

    private static final long serialVersionUID = 1L;
    MyButtonListener myButtonListener;
    Random random;
    JList elementList;
    Vector<String> elements;
    JLabel imageLabel;
    JPanel mainCardPanel;
    CardLayout mainCardLayout;
    JTextField nameField;
    JTextArea articleArea;
    JTextField titleField;
    JTextField imageTextField;
    public static final Color PICTURE_BLOCK_COLOR = new Color(50, 50, 50);
    public static final Color DEFAULT_BACKGROUND_COLOR = new Color(243, 243, 243);
    public static final Color DEFAULT_TEXT_COLOR = new Color(0, 0, 0);
    public static final Color DEFAULT_COMMON_ARTICLE_COLOR = DEFAULT_BACKGROUND_COLOR;
    public static Color pictureBoundColor = new Color(0, 0, 0);
    static Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
    static Color textColor = new Color(0, 0, 0);
    Color nameColor;
    public static final Color HEADER_COLOR = new Color(200, 200, 200);
    public static final int POST_NAME_MAX_SIZE = 22;
    public static final int TITLE_MAX_SIZE = 32;
    public static final int PICTURE_COMMENT_MAX_SIZE = 60;
    static Font textFont;
    static Font commentFont;
    static Font headerFont;
    static Font postNameFont;
    static int lineSize = 59;
    static final int pictureDescriptionLineSize = 33;
    static final int width = 600;
    Vector<MaterialBlock> materialBlocks;
    String name;
    int longPostSize;
    BufferedImage result;
    JLabel imageOnEditLabel;
    Image currentImage;
    MaterialBlock selectedBlock;
    int selectedIndex;
    JButton applyTitle;
    JButton applyName;
    JButton applyBackgroundColor;
    JButton applyTextColor;
    Color commonArticleBackgroundColor;
    static DPGenGUI singleGenGUI;
    Vector<String> signature;
    Color signatureColor;
    JTextField signatureField;
    JTextField textSizeField;
    static Color thematicColor;
    JPopupMenu popupMenu;
    JMenuItem popupBackColorItem;
    JMenuItem popupTextColorItem;
    static int style;
    ElementFactory elementFactory;
    JList styleSelectionList;
    JButton applyArticle;
    Vector<String> listOfStyles;
    JMenu rotateMenu;

    DPGenGUI(String s){
        super(s);
        myButtonListener = new MyButtonListener(this);
        MyListSelectionListener myListSelectionListener = new MyListSelectionListener(this);
        MyImageMouseListener imageMouseListener = new MyImageMouseListener(this);
        MyStyleSelectionListener styleSelectionListener = new MyStyleSelectionListener(this);

        elementFactory = new ElementFactory(ElementFactory.STYLE_ORIGINAL);
        random = new Random();
        setFont(new Font("Dialog", Font.PLAIN, 15));
        longPostSize = 1000;
        name = "";
        textFont = new Font("Arial", Font.PLAIN, 18);
        postNameFont = new Font("Arial", Font.PLAIN, 48);
        commentFont = textFont.deriveFont(14f);
        headerFont = new Font("Arial", Font.BOLD, 28);
        selectedIndex = -1;
        commonArticleBackgroundColor = backgroundColor;
        nameColor = new Color(0, 0, 0);
        signatureColor = new Color(100, 100, 100);
        signature = new Vector<String>();
        thematicColor = new Color(0, 0, 0);
        style = ElementFactory.STYLE_ORIGINAL;
        listOfStyles = new Vector<String>();
        listOfStyles.add("Original style");
        listOfStyles.add("Basic style");
        listOfStyles.add("Dark style");
        listOfStyles.add("Lonely42Luckily");
        listOfStyles.add("Pictures only style");

        materialBlocks = new Vector<MaterialBlock>();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        Container c = getContentPane();
        JPanel leftPanel = new JPanel(null, true);
        leftPanel.setPreferredSize(new Dimension(300, 800));
        JPanel rightPanel = new JPanel(null, true);
        rightPanel.setPreferredSize(new Dimension(200, 800));
        JPanel centerPanel = new JPanel(null, true);
        centerPanel.setBackground(Color.GRAY);
        c.add(leftPanel, BorderLayout.WEST);
        c.add(rightPanel, BorderLayout.EAST);
        Font buttonFont = new Font("Dialog", Font.PLAIN, 20);

        //left panel
        JButton optionsButton = new JButton("Настройки поста");
        optionsButton.setBounds(10, 10, 280, 50);
        optionsButton.setFont(buttonFont);
        optionsButton.addActionListener(myButtonListener);
        leftPanel.add(optionsButton);

        JButton createNewArticleButton = new JButton("Создать абзац");
        createNewArticleButton.setBounds(10, 70, 280, 50);
        createNewArticleButton.setFont(buttonFont);
        createNewArticleButton.addActionListener(myButtonListener);
        leftPanel.add(createNewArticleButton);

        JButton createNewTitleButton = new JButton("Создать заголовок");
        createNewTitleButton.setBounds(10, 130, 280, 50);
        createNewTitleButton.setFont(buttonFont);
        createNewTitleButton.addActionListener(myButtonListener);
        leftPanel.add(createNewTitleButton);

        JButton createNewPictureButton = new JButton("Создать картинку");
        createNewPictureButton.setBounds(10, 190, 280, 50);
        createNewPictureButton.setFont(buttonFont);
        createNewPictureButton.addActionListener(myButtonListener);
        leftPanel.add(createNewPictureButton);

        //now right panel
        elements = new Vector<String>();
        elementList = new JList(elements);
        elementList.addListSelectionListener(myListSelectionListener);
        JScrollPane elementPane = new JScrollPane(elementList);
        elementPane.setBounds(5, 115, 190, 300);
        rightPanel.add(elementPane);

        JLabel editLabel = new JLabel("Редактирование элемента");
        editLabel.setBounds(10, 5, 190, 20);
        rightPanel.add(editLabel);

        JButton deleteButton = new JButton("Удалить");
        deleteButton.setBounds(5, 25, 190, 25);
        deleteButton.addActionListener(myButtonListener);
        rightPanel.add(deleteButton);

        JButton cloneButton = new JButton("Клонировать");
        cloneButton.setBounds(5, 85, 190, 25);
        cloneButton.addActionListener(myButtonListener);
        rightPanel.add(cloneButton);

        JButton moveUpButton = new JButton("Выше");
        moveUpButton.setBounds(5, 55, 95, 25);
        moveUpButton.addActionListener(myButtonListener);
        rightPanel.add(moveUpButton);

        JButton moveDownButton = new JButton("Ниже");
        moveDownButton.setBounds(100, 55, 95, 25);
        moveDownButton.addActionListener(myButtonListener);
        rightPanel.add(moveDownButton);

        JLabel styleLabel = new JLabel("Стили");
        styleLabel.setBounds(5, 420, 190, 25);
        rightPanel.add(styleLabel);

        styleSelectionList = new JList();
        styleSelectionList.addListSelectionListener(styleSelectionListener);
        styleSelectionList.setListData(listOfStyles);
        JScrollPane styleScrollPane = new JScrollPane(styleSelectionList);
        styleScrollPane.setBounds(5, 445, 190, 190);
        rightPanel.add(styleScrollPane);

        //center panel
        JScrollPane centerScrollPane = new JScrollPane(centerPanel);
        c.add(centerScrollPane, BorderLayout.CENTER);
        Image emptyPost = new BufferedImage(width, 630, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = emptyPost.getGraphics();
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0, 0, width, 1000);
        ImageIcon icon = new ImageIcon(emptyPost);
        imageLabel = new JLabel(icon);
        imageLabel.setOpaque(true);
        imageLabel.setBackground(Color.DARK_GRAY);
        imageLabel.addMouseListener(imageMouseListener);
        centerPanel.setLayout(new BorderLayout());
        centerScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        centerPanel.add(imageLabel);

        //context menu
        popupMenu = new JPopupMenu();
        Vector<String> popupMenuNames = new Vector<String>();
        popupMenuNames.add("Удалить");
        popupMenuNames.add("Выше");
        popupMenuNames.add("Ниже");
        popupMenuNames.add("Клонировать");
        for (String popupName : popupMenuNames) {
            JMenuItem item = new JMenuItem(popupName);
            item.addActionListener(myButtonListener);
            popupMenu.add(item);
        }
        popupBackColorItem = new JMenuItem("Задать цвет фона");
        popupBackColorItem.addActionListener(myButtonListener);
        popupMenu.add(popupBackColorItem);
        popupTextColorItem = new JMenuItem("Задать цвет текста");
        popupTextColorItem.addActionListener(myButtonListener);
        popupMenu.add(popupTextColorItem);
        JMenu selectStyleItem = new JMenu("Стиль элемента");
        for (String styleName : listOfStyles) {
            JMenuItem item = new JMenuItem(styleName);
            item.setActionCommand(styleName);
            item.addActionListener(styleSelectionListener);
            selectStyleItem.add(item);
        }
        popupMenu.add(selectStyleItem);
        rotateMenu = new JMenu("Повернуть");
        Vector<String> rotateList = new Vector<String>();
        rotateList.add("По часовой");
        rotateList.add("Против часовой");
        rotateList.add("Вверх ногами");
        for (String rotateOption : rotateList) {
            JMenuItem item = new JMenuItem(rotateOption);
            item.setActionCommand(rotateOption);
            item.addActionListener(myButtonListener);
            rotateMenu.add(item);
        }
        popupMenu.add(rotateMenu);

        //main card panel
        mainCardPanel = new JPanel();
        mainCardLayout = new CardLayout();
        mainCardPanel.setLayout(mainCardLayout);
        mainCardPanel.setBounds(10, 250, 280, 400);
        leftPanel.add(mainCardPanel);

        JPanel emptyPanel = new JPanel();
        mainCardPanel.add(emptyPanel, "empty");
        mainCardLayout.show(mainCardPanel, "empty");

        //options for post panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(null);
        JLabel nameLabel = new JLabel("Название поста");
        nameLabel.setBounds(5, 5, 150, 20);
        optionsPanel.add(nameLabel);
        nameField = new JTextField("", 20);
        nameField.setBounds(90, 5, 185, 25);
        nameField.setActionCommand("name");
        nameField.addActionListener(this);
        optionsPanel.add(nameField);
        applyName = new JButton("Применить название");
        applyName.addActionListener(myButtonListener);
        applyName.setBounds(5, 30, 150, 25);
        optionsPanel.add(applyName);
        JButton changeNameColor = new JButton("Цвет названия");
        changeNameColor.addActionListener(myButtonListener);
        changeNameColor.setBounds(160, 30, 115, 25);
        optionsPanel.add(changeNameColor);
        applyBackgroundColor = new JButton("Задать фон");
        applyBackgroundColor.setActionCommand("apply post background color");
        applyBackgroundColor.addActionListener(myButtonListener);
        applyBackgroundColor.setBounds(5, 60, 115, 25);
        optionsPanel.add(applyBackgroundColor);
        applyTextColor = new JButton("Задать цвет текста");
        applyTextColor.setActionCommand("apply post text color");
        applyTextColor.addActionListener(myButtonListener);
        applyTextColor.setBounds(125, 60, 150, 25);
        optionsPanel.add(applyTextColor);
        JButton setCommonArticleBackgroundColor = new JButton("Задать общий цвет абзацев");
        setCommonArticleBackgroundColor.setActionCommand("set common article color");
        setCommonArticleBackgroundColor.addActionListener(myButtonListener);
        setCommonArticleBackgroundColor.setBounds(5, 90, 270, 25);
        optionsPanel.add(setCommonArticleBackgroundColor);
        JLabel labelSignature = new JLabel("Подпись");
        labelSignature.setBounds(5, 130, 270, 25);
        optionsPanel.add(labelSignature);
        signatureField = new JTextField(getStringFromStringVector(signature));
        signatureField.setActionCommand("Применить подпись");
        signatureField.addActionListener(myButtonListener);
        signatureField.setBounds(5, 150, 270, 25);
        optionsPanel.add(signatureField);
        JButton applySignatureButton = new JButton("Применить подпись");
        applySignatureButton.addActionListener(myButtonListener);
        applySignatureButton.setBounds(5, 180, 140, 25);
        optionsPanel.add(applySignatureButton);
        JButton setSignatureColorButton = new JButton("Цвет подписи");
        setSignatureColorButton.addActionListener(myButtonListener);
        setSignatureColorButton.setBounds(150, 180, 125, 25);
        optionsPanel.add(setSignatureColorButton);
        JButton applyTextSize = new JButton("Задать размер текста");
        applyTextSize.addActionListener(myButtonListener);
        applyTextSize.setBounds(5, 210, 150, 25);
        optionsPanel.add(applyTextSize);
        textSizeField = new JTextField(String.valueOf(textFont.getSize()));
        textSizeField.setBounds(160, 210, 115, 25);
        textSizeField.addActionListener(myButtonListener);
        textSizeField.setActionCommand("Задать размер текста");
        optionsPanel.add(textSizeField);
        JButton applyThematicColor = new JButton("Задать тематический цвет поста O_o");
        applyThematicColor.addActionListener(myButtonListener);
        applyThematicColor.setBounds(5, 240, 270, 25);
        optionsPanel.add(applyThematicColor);
        mainCardPanel.add(optionsPanel, "options");

        //edit article panel
        JPanel editArticlePanel = new JPanel();
        editArticlePanel.setLayout(null);
        JLabel articleLabel = new JLabel("Текст абзаца");
        articleLabel.setBounds(5, 5, 150, 20);
        editArticlePanel.add(articleLabel);
        articleArea = new JTextArea("", lineSize, 5);
        articleArea.setLineWrap(true);
        JScrollPane tempScrollPane = new JScrollPane(articleArea);
        tempScrollPane.setBounds(5, 30, 270, 300);
        articleArea.setFont(c.getFont());
        editArticlePanel.add(tempScrollPane);
        applyArticle = new JButton("Применить абзац");
        applyArticle.addActionListener(myButtonListener);
        applyArticle.setBounds(5, 335, 270, 25);
        editArticlePanel.add(applyArticle);
        JButton changeArticleBackgroundColor = new JButton("Задать цвет фона");
        changeArticleBackgroundColor.addActionListener(myButtonListener);
        changeArticleBackgroundColor.setActionCommand("change article background color");
        changeArticleBackgroundColor.setBounds(5, 365, 130, 25);
        editArticlePanel.add(changeArticleBackgroundColor);
        JButton changeArticleTextColor = new JButton("Задать цвет текста");
        changeArticleTextColor.addActionListener(myButtonListener);
        changeArticleTextColor.setActionCommand("change article text color");
        changeArticleTextColor.setBounds(140, 365, 135, 25);
        editArticlePanel.add(changeArticleTextColor);
        mainCardPanel.add(editArticlePanel, "article");

        //edit title panel
        JPanel editTitlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Заголовок");
        titleLabel.setBounds(5, 5, 150, 20);
        editTitlePanel.add(titleLabel);
        titleField = new JTextField("заголовок", 20);
        titleField.setBounds(5, 30, 100, 25);
        titleField.setActionCommand("title");
        titleField.addActionListener(this);
        editTitlePanel.add(titleField);
        applyTitle = new JButton("Применить заголовок");
        applyTitle.addActionListener(myButtonListener);
//        applyTitle.setBounds(5, 60, 290, 25);
        editTitlePanel.add(applyTitle);
        JButton changeTitleColor = new JButton("Цвет заголовка");
        changeTitleColor.addActionListener(myButtonListener);
        editTitlePanel.add(changeTitleColor);
        mainCardPanel.add(editTitlePanel, "title");

        //edit picture panel
        JPanel editImagePanel = new JPanel();
        editImagePanel.setLayout(null);
        JLabel editImageLabel = new JLabel("Скопируйте картинку и нажмите -->");
        editImageLabel.setBounds(5, 5, 200, 20);
        editImagePanel.add(editImageLabel);
        JButton pasteImageButton = new JButton("Вставить");
        pasteImageButton.setBounds(195, 0, 80, 25);
        pasteImageButton.addActionListener(myButtonListener);
        editImagePanel.add(pasteImageButton);
        imageOnEditLabel = new JLabel();
        imageOnEditLabel.setBounds(5, 25, 270, 270);
        Image image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics gr = image.getGraphics();
        gr.setColor(Color.LIGHT_GRAY);
        gr.fillRect(0, 0, 300, 300);
        ImageIcon imageIcon = new ImageIcon(image);
        imageOnEditLabel.setIcon(imageIcon);
        editImagePanel.add(imageOnEditLabel);
        imageTextField = new JTextField("Надпись на картинке", 20);
        imageTextField.setBounds(5, 300, 270, 25);
        editImagePanel.add(imageTextField);
        JButton applyImage = new JButton("Применить картинку");
        applyImage.addActionListener(myButtonListener);
        applyImage.setBounds(5, 330, 270, 25);
        editImagePanel.add(applyImage);
        JButton addImageFromFileButton = new JButton("Добавить картинку из папки");
        addImageFromFileButton.addActionListener(myButtonListener);
        addImageFromFileButton.setBounds(5, 360, 270, 25);
        editImagePanel.add(addImageFromFileButton);
        mainCardPanel.add(editImagePanel, "picture");

        //menu bar
        MenuBar menuBar = new MenuBar();

        setMenuBar(menuBar);
        Menu main = new Menu("Файл");
        menuBar.add(main);

        Vector<String> menuItemNames = new Vector<String>();
        menuItemNames.add("Сохранить");
        menuItemNames.add("Загрузить");
        menuItemNames.add("Обновить пост");
        menuItemNames.add("Новый пост");
        menuItemNames.add("Экспорт в PNG");
        menuItemNames.add("Помощь, епта");
        menuItemNames.add("Quit");
        Vector<MenuItem> menuItems = new Vector<MenuItem>();
        for (String name : menuItemNames) {
            MenuItem menuItem = new MenuItem(name);
            main.add(menuItem);
            menuItems.add(menuItem);
        }
        Vector<MenuShortcut> menuShortcuts = new Vector<MenuShortcut>();
        menuShortcuts.add(new MenuShortcut(KeyEvent.VK_S, false));
        menuShortcuts.add(new MenuShortcut(KeyEvent.VK_O, false));
        menuShortcuts.add(new MenuShortcut(KeyEvent.VK_R, false));
        menuShortcuts.add(new MenuShortcut(KeyEvent.VK_N, false));
        menuShortcuts.add(new MenuShortcut(KeyEvent.VK_E, false));
        menuShortcuts.add(new MenuShortcut(KeyEvent.VK_H, false));
        menuShortcuts.add(new MenuShortcut(KeyEvent.VK_Q, false));

        int size = Math.min(menuItems.size(), menuShortcuts.size());
        for (int i=0; i<size; i++) {
            menuItems.get(i).setShortcut(menuShortcuts.get(i));
            menuItems.get(i).addActionListener(myButtonListener);
        }

        setBounds(50, 20, 1200, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public static void setBackgroundColor(Color backgroundColor) {
        DPGenGUI.backgroundColor = backgroundColor;
        DPGenGUI.singleGenGUI.signatureColor = backgroundColor.darker().darker();
    }

    public void setStyle(int style) {
        this.style = style;
        elementFactory.setStyle(style);
        Vector<MaterialBlock> copies = new Vector<MaterialBlock>();
        for (MaterialBlock materialBlock : materialBlocks) {
            copies.add(elementFactory.copyOfElement(materialBlock, style));
        }
        clearPost();
        Color backgroundArticleColor = elementFactory.getArticleBackgroundOfStyle();
        for (MaterialBlock copy : copies) {
            if (copy instanceof Article) ((Article) copy).setBackgroundColor(backgroundArticleColor);
            addElement(copy);
        }
        setTextColor(elementFactory.getTextColorOfStyle());
        setBackgroundColor(elementFactory.getBackgroundColorOfStyle());
        if (style == ElementFactory.STYLE_LONELY_LUCKILY) {
            textFont = textFont.deriveFont(16.0f);
            refreshLineSize();
        }
        compose(false);
    }

    public void setStyleByString(String nameOfStyle) {
        setStyle(getStyleByName(nameOfStyle));
    }

    public int getStyleByName(String nameOfStyle) {
        if (nameOfStyle.equals(listOfStyles.get(0))) return ElementFactory.STYLE_ORIGINAL;
        else if (nameOfStyle.equals(listOfStyles.get(1))) return ElementFactory.STYLE_BASIC;
        else if (nameOfStyle.equals(listOfStyles.get(2))) return ElementFactory.STYLE_DARK;
        else if (nameOfStyle.equals(listOfStyles.get(3))) return ElementFactory.STYLE_LONELY_LUCKILY;
        else if (nameOfStyle.equals(listOfStyles.get(4))) return ElementFactory.STYLE_PICTURES_ONLY;

        //if bad name of style
        System.out.println("tried to get style by name but failed");
        return ElementFactory.STYLE_ORIGINAL;
    }

    public void setStyleOfSelectedBlockByString(String nameOfStyle) {
        if (selectedBlock == null) return;
        if (selectedIndex < 0 || selectedIndex >= materialBlocks.size()) return;
        if (materialBlocks.size() < 1) return;
        MaterialBlock copy = elementFactory.copyOfElement(selectedBlock, getStyleByName(nameOfStyle));
        materialBlocks.add(selectedIndex, copy);
        materialBlocks.remove(selectedBlock);
        copy.refreshHeight();
        compose();
    }

    public void setCommonArticleBackgroundColor(Color commonArticleBackgroundColor) {
        this.commonArticleBackgroundColor = commonArticleBackgroundColor;
        for (MaterialBlock materialBlock : materialBlocks) {
            if (materialBlock instanceof Article) {
                ((Article) materialBlock).setBackgroundColor(commonArticleBackgroundColor);
            }
        }
        compose(false);
    }

    public void setThematicColor(Color thematicColor) {
        this.thematicColor = thematicColor;
        pictureBoundColor = thematicColor;
    }

    public void refreshLineSize() {
        lineSize = howManySymbolsFitInWidth(width - 20, textFont);
        for (MaterialBlock materialBlock : materialBlocks) {
            if (materialBlock instanceof Article) {
                ((Article) materialBlock).refreshArticleLines();
            }
        }
        compose(false);
    }

    private static String generateStringByLength(int length) {
        StringBuffer buffer = new StringBuffer();
        for (int i=0; i<length; i++) buffer.append("e");
        return buffer.toString();
    }

    public void refreshLongPostSize() {
        longPostSize = getEstimatedPostHeight();
    }

    public static void setTextColor(Color textColor) {
        DPGenGUI.textColor = textColor;
    }

    public void refreshTextColorOfArticles() {
        for (MaterialBlock materialBlock : materialBlocks) {
            if (materialBlock instanceof Article)
                ((Article) materialBlock).setTextColor(textColor);
        }
    }

    public void askIfUserWantsToCloseProgram() {
        Object[] options = {"Выйти", "Сохранить и выйти", "Отмена"};
        int n = JOptionPane.showOptionDialog(singleGenGUI, "Выйти без сохранения?", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        switch (n) {
            case JOptionPane.YES_OPTION: System.exit(0); break;
            case JOptionPane.NO_OPTION:
                savePost();
                System.exit(0);
                break;
        }
    }

    public static void main(String args[]){
        singleGenGUI = new DPGenGUI("Генератор длиннопостов от yiotro ;)");
        singleGenGUI.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent ev){
                singleGenGUI.askIfUserWantsToCloseProgram();
            }
        });
    }

    public static DPGenGUI getSingleGenGUI() {
        return singleGenGUI;
    }

    public void clearPost() {
        materialBlocks = new Vector<MaterialBlock>();
        elementList.clearSelection();
        elementList.removeAll();
        elementList.updateUI();
        elements.removeAllElements();
        clearSelection();
        backgroundColor = DEFAULT_BACKGROUND_COLOR;
        textColor = DEFAULT_TEXT_COLOR;
        commonArticleBackgroundColor = DEFAULT_COMMON_ARTICLE_COLOR;
        compose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("title")) {
            applyTitle.doClick();
        } else if (command.equals("name")) {
            applyName.doClick();
        }
    }

    void exportToPNG() {
        compose();
        JFileChooser chooser = new JFileChooser(".");
        FileFilter type1 = new FileNameExtensionFilter("Image file", ".png");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(type1);
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = new File(chooser.getSelectedFile() + ".png");
                ImageIO.write(result, "png", file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void savePost() {
        JFileChooser chooser = new JFileChooser(".");
        FileFilter type1 = new FileNameExtensionFilter("Pikabu post", ".dat");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(type1);
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
                ProjectInformation projectInformation = new ProjectInformation(getAsPiBlocks(), name, backgroundColor, textColor, commonArticleBackgroundColor, nameColor, thematicColor, textFont, style, signature);
                stream.writeObject(projectInformation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void loadPost() {
        JFileChooser chooser = new JFileChooser();
//        FileFilter type1 = new FileNameExtensionFilter("Pikabu post", ".dat");
//        chooser.setAcceptAllFileFilterUsed(false);
//        chooser.addChoosableFileFilter(type1);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            refreshLineSize();
            try {
                File file = chooser.getSelectedFile();
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
                ProjectInformation projectInformation = (ProjectInformation) stream.readObject();
                clearPost();
                setProjectByProjectInformation(projectInformation);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage() + "\n");
                e.printStackTrace();
            }
        }
    }

    void mousePressedOnImage(MouseEvent mouseEvent) {
        int horIconPosEstimated = (int) ((float)(imageLabel.getWidth() - width) / 2.0f);
        int verIconPosEstimated = Math.max(3, (635 - longPostSize) / 2);
        int cx = mouseEvent.getX() - horIconPosEstimated;
        int cy = mouseEvent.getY() - verIconPosEstimated;
        if (cx < 0 || cy < 0 || cx > width) return;
        clearSelection();
        Rectangle region;
        boolean selectedSomething = false;
        for (MaterialBlock materialBlock : materialBlocks) {
            region = materialBlock.getSelectionRegion();
            if (cx > region.x && cx < region.x + region.width && cy > region.y && cy < region.y + region.height) {
                int index = materialBlocks.indexOf(materialBlock);
                selectSpecificBlock(index);
                selectedSomething = true;
            }
        }
        if (!selectedSomething) compose(false);
        if (selectedSomething && mouseEvent.getButton() == MouseEvent.BUTTON3) {
            popupBackColorItem.setEnabled(false);
            popupTextColorItem.setEnabled(false);
            rotateMenu.setEnabled(false);
            if (selectedBlock instanceof Article) {
                popupBackColorItem.setEnabled(true);
                popupTextColorItem.setEnabled(true);
            } else if (selectedBlock instanceof Title) {
                popupTextColorItem.setEnabled(true);
            } else if (selectedBlock instanceof PictureBlock) {
                rotateMenu.setEnabled(true);
            }
            popupMenu.show(imageLabel, mouseEvent.getX(), mouseEvent.getY());
        }
    }

    void selectSpecificBlock(int index) {
        clearSelection();
        selectedIndex = index;
        reselectBlock();
    }

    void compose() {
        compose(true);
    }

    void compose(boolean reselect) {
        int currentVerticalPos = 0;
        longPostSize = getEstimatedPostHeight();
        result = new BufferedImage(width, longPostSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) result.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setFont(postNameFont);
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, longPostSize);
        graphics.setStroke(new BasicStroke(3));
        graphics.setColor(Color.GRAY);
        graphics.drawRect(1, 1, width - 3, longPostSize - 3);
        int namePos = width / 2 - 13 * name.toString().length();
        graphics.setColor(nameColor);
        graphics.drawString(name.toString(), namePos, 70);
        graphics.setColor(textColor);
        graphics.setFont(textFont);
        currentVerticalPos = 150;
        for (MaterialBlock materialBlock : materialBlocks) {
            currentVerticalPos = materialBlock.show(graphics, currentVerticalPos);
        }
        //signature
        graphics.setColor(signatureColor);
        graphics.setFont(textFont);
        currentVerticalPos += 10;
        int horizontalPosition;
        for (String line : signature) {
            FontMetrics metrics = graphics.getFontMetrics();
            int currentLineWidth = metrics.stringWidth(line);
            horizontalPosition = width - 10 - currentLineWidth;
            graphics.drawString(line, horizontalPosition, currentVerticalPos);
            currentVerticalPos += 24;
        }

        BufferedImage showedPostImage = new BufferedImage(result.getWidth(null), result.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics gr = showedPostImage.getGraphics();
        gr.drawImage(result, 0, 0, null);

        //selection show
        if (selectedBlock != null) {
            Rectangle selection = selectedBlock.getSelectionRegion();
            if (selection != null) {
                gr.setColor(Color.RED);
                gr.drawRect(selection.x, selection.y, selection.width, selection.height);
            }
        }
        imageLabel.setIcon(new ImageIcon(showedPostImage));

        //this is not prevent crazy fucking shit glitch
        if (reselect) reselectBlock();
    }

    public static Image rotateImage(Image img, double angle){
        double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = img.getWidth(null), h = img.getHeight(null);
        int newW = (int) Math.floor(w * cos + h * sin), newH = (int) Math.floor(h
                * cos + w * sin);
        BufferedImage bImg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bImg.createGraphics();
        g.translate((newW - w) / 2, (newH - h) / 2);
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawRenderedImage((BufferedImage) img, null);
        g.dispose();
        return bImg;
    }

    public void clearSelection() {
        selectedBlock = null;
        selectedIndex = -1;
        elementList.clearSelection();
        mainCardLayout.show(mainCardPanel, "empty");
    }

    public void reselectBlock() {
        elementList.clearSelection();
        elementList.setSelectedIndex(selectedIndex);
    }

    void updateResultSelectionWithoutComposing() {
        BufferedImage showedPostImage = new BufferedImage(result.getWidth(null), result.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = (Graphics2D) showedPostImage.getGraphics();
        gr.drawImage(result, 0, 0, null);
        //selection show
        if (selectedBlock != null) {
            Rectangle selection = selectedBlock.getSelectionRegion();
            if (selection != null) {
                gr.setColor(Color.RED);
                float dash[] = {5.0f,5.0f};
                BasicStroke dashedStroke = new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.5f, dash, 0);
                gr.setStroke(dashedStroke);
                gr.drawRect(selection.x, selection.y, selection.width, selection.height);
            }
        }
        imageLabel.setIcon(new ImageIcon(showedPostImage));
    }

    void deleteSelectedBlock() {
        if (selectedBlock == null) return;
        if (selectedIndex < 0 || selectedIndex >= materialBlocks.size()) return;
        if (materialBlocks.size() < 1) return;
        MaterialBlock blockFromList = materialBlocks.get(selectedIndex);
        if (blockFromList != selectedBlock) System.out.println("somehow selected block and selected index are not the same...");
        materialBlocks.remove(selectedIndex);
        elements.remove(selectedIndex);
        elementList.setListData(elements);
        clearSelection();
        compose();
    }

    void cloneSelectedBlock() {
        if (selectedBlock == null) return;
        if (selectedIndex < 0 || selectedIndex >= materialBlocks.size()) return;
        if (materialBlocks.size() < 1) return;
        MaterialBlock blockFromList = materialBlocks.get(selectedIndex);
        if (blockFromList != selectedBlock) System.out.println("somehow selected block and selected index are not the same...");
        addElement(selectedIndex, elementFactory.copyOfElement(selectedBlock));
        compose();
    }

    int getEstimatedPostHeight() {
        int height = 170;
        for (MaterialBlock materialBlock : materialBlocks) {
            height += materialBlock.getHeight();
        }
        height += 10 + 24 * signature.size();
        return height;
    }

    void moveUpSelectedBlock() {
        if (selectedBlock == null) return;
        if (selectedIndex < 1 || selectedIndex >= materialBlocks.size()) return;
        if (materialBlocks.size() < 1) return;
        MaterialBlock blockFromList = materialBlocks.get(selectedIndex);
        if (blockFromList != selectedBlock) System.out.println("somehow selected block and selected index are not the same...");
        MaterialBlock upperBlock = materialBlocks.get(selectedIndex - 1);
        materialBlocks.setElementAt(selectedBlock, selectedIndex - 1);
        materialBlocks.setElementAt(upperBlock, selectedIndex);
        String upperElement = elements.get(selectedIndex - 1);
        String currentElement = elements.get(selectedIndex);
        elements.setElementAt(currentElement, selectedIndex - 1);
        elements.setElementAt(upperElement, selectedIndex);
        selectedIndex--;
        elementList.setListData(elements);
        compose();
    }

    public static String getStringFromStringVector(Vector<String> vector, boolean newLineSymbol) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String item : vector) {
            if (item.charAt(item.length()-1) == ' ') item = item.substring(0, item.length()-1);
            if (newLineSymbol) stringBuffer.append(item + "\n");
            else stringBuffer.append(item);
        }
        return stringBuffer.toString();
    }

    public static String getStringFromStringVector(Vector<String> vector) {
        return getStringFromStringVector(vector, true);
    }

    void moveDownSelectedBlock() {
        if (selectedBlock == null) return;
        if (selectedIndex < 0 || selectedIndex >= materialBlocks.size() - 1) return;
        if (materialBlocks.size() < 1) return;
        MaterialBlock blockFromList = materialBlocks.get(selectedIndex);
        if (blockFromList != selectedBlock) System.out.println("somehow selected block and selected index are not the same...");
        MaterialBlock lowerBlock = materialBlocks.get(selectedIndex + 1);
        materialBlocks.setElementAt(selectedBlock, selectedIndex + 1);
        materialBlocks.setElementAt(lowerBlock, selectedIndex);
        String lowerElement = elements.get(selectedIndex + 1);
        String currentElement = elements.get(selectedIndex);
        elements.setElementAt(currentElement, selectedIndex + 1);
        elements.setElementAt(lowerElement, selectedIndex);
        selectedIndex++;
        elementList.setListData(elements);
        compose();
    }

    public Vector<PIBlock> getAsPiBlocks() {
        Vector<PIBlock> piBlocks = new Vector<PIBlock>();
        PIBlock block;
        for (MaterialBlock materialBlock : materialBlocks) {
            block = new PIBlock();
            if (materialBlock instanceof Article) {
                block.type = PIBlock.TYPE_ARTICLE;
                block.text = ((Article)materialBlock).article;
                block.textColor = ((Article) materialBlock).textColor;
                block.backColor = ((Article) materialBlock).backgroundColor;
                block.bordered = ((Article) materialBlock).bordered;
                block.style = materialBlock.style;
            } else if (materialBlock instanceof Title) {
                block.type = PIBlock.TYPE_TITLE;
                block.text = new Vector<String>();
                block.text.add(((Title)materialBlock).header);
                block.textColor = ((Title) materialBlock).textColor;
                block.style = materialBlock.style;
            } else if (materialBlock instanceof PictureBlock) {
                block.type = PIBlock.TYPE_PICTURE;
                block.text = new Vector<String>();
                block.text.add(((PictureBlock)materialBlock).description);
                block.imageIcon = new ImageIcon(((PictureBlock)materialBlock).picture);
                block.style = materialBlock.style;
            }
            piBlocks.add(block);
        }
        return piBlocks;
    }

    public void setProjectByProjectInformation(ProjectInformation projectInformation) {
        name = projectInformation.projectName;
        style = projectInformation.style;
        Vector<PIBlock> piBlocks = projectInformation.blocks;
        for (PIBlock block : piBlocks) {
            switch (block.type) {
                case PIBlock.TYPE_ARTICLE:
                    Article article = elementFactory.createArticle(block.text, block.style);
                    article.setBackgroundColor(block.backColor);
                    article.setTextColor(block.textColor);
                    article.setBordered(block.bordered);
                    addElement(article);
                    break;
                case PIBlock.TYPE_PICTURE:
                    String text = "";
                    if (block.text.size() > 0) text = block.text.firstElement();
                    PictureBlock pictureBlock = elementFactory.createPictureBlock(block.imageIcon.getImage(), text, block.style);
                    addElement(pictureBlock);
                    break;
                case PIBlock.TYPE_TITLE:
                    Title header = elementFactory.createTitle(block.text.firstElement(), block.style);
                    header.setTextColor(block.textColor);
                    addElement(header);
                    break;
            }
        }
        backgroundColor = projectInformation.backgroundColor;
        textColor = projectInformation.textColor;
        commonArticleBackgroundColor = projectInformation.commonArticleColor;
        nameColor = projectInformation.nameColor;
        thematicColor = projectInformation.thematicColor;
        pictureBoundColor = thematicColor;
        textFont = projectInformation.textFont;
        signature = projectInformation.sign;
        signatureField.setText(getStringFromStringVector(signature));
        compose();
    }

    public static int howManySymbolsFitInWidth(int width, Font font) {
        BufferedImage bufferedImage = new BufferedImage(width, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setFont(font);
        FontMetrics metrics = graphics.getFontMetrics();
        int answer = 1;
        while (metrics.stringWidth(generateStringByLength(answer)) < width) {
            answer++;
        }
        return answer;
    }

    public void addElement(int index, MaterialBlock element) {
        elementList.clearSelection();
        if (element instanceof PictureBlock) elements.add(index, "Картинка");
        if (element instanceof Article) elements.add(index, "Абзац");
        if (element instanceof Title) elements.add(index, "Заголовок");
        elementList.setListData(elements);
        materialBlocks.add(index, element);
        compose();
    }

    public void addElement(MaterialBlock element) {
        elementList.clearSelection();
        if (element instanceof PictureBlock) elements.add("Картинка");
        if (element instanceof Article) elements.add("Абзац");
        if (element instanceof Title) elements.add("Заголовок");
        elementList.setListData(elements);
        materialBlocks.add(element);
        element.refreshHeight();
        compose();
    }

    public static Image getCopyOfImage(Image src) {
        Image copy = new BufferedImage(src.getWidth(null), src.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = copy.getGraphics();
        graphics.drawImage(src, 0, 0, null);
        return copy;
    }

    public static Image getResizedImage(Image image, int wantedWidth, int wantedHeight) {
        BufferedImage resizedImage = new BufferedImage(wantedWidth, wantedWidth, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, wantedWidth, wantedHeight, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        if (resizedImage.getHeight(null) > wantedHeight)
            resizedImage = resizedImage.getSubimage(0, 0, wantedWidth, wantedHeight);
        return resizedImage;
    }

    public static Image getResizedImageByWidth(Image image, int wantedWidth) {
        double k = ((double) image.getHeight(null)) / ((double) image.getWidth(null));
        int wantedHeight = (int) (k * wantedWidth);
        return getResizedImage(image, wantedWidth, wantedHeight);
    }

    public static Image getResizedImageByHeight(Image image, int wantedHeight) {
        double k = ((double) image.getWidth(null)) / ((double) image.getHeight(null));
        int wantedWidth = (int) (k * wantedHeight);
        return getResizedImage(image, wantedWidth, wantedHeight);
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {}
}

